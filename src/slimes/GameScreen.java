package slimes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import slimes.slime.Gender;
import slimes.slime.Slime;
import slimes.slime.WombSlime;
import slimes.tools.PlacedTool;
import slimes.tools.Tool;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.Util;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class renders all the visuals for the game.
 *
 * @author Rene
 */

public class GameScreen
{
    //how much the inventory should be scaled
    private static final double INVENTORY_SCALE_FACTOR = 2;
    private static final int INVENTORY_PADDING = 4;
    public static final int ITEM_ICON_WIDTH = 32;
    public static final int ITEM_ICON_WIDTH_PLUS_PADDING = ITEM_ICON_WIDTH + INVENTORY_PADDING;

    // The width and height (in pixels) of each cell that makes up the game.
    public static final int GRID_CELL_WIDTH = 32;
    public static final int GRID_CELL_HEIGHT = 32;

    @FXML
    public Canvas map;

    @FXML
    public Canvas inventory;

    @FXML
    public Label scoreText;

    @FXML
    public Canvas populationBar;

    @FXML
    public Label timerText;

    public GameScreen()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
            if (Main.getScreen() == Screen.GAME) {
                tick();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(50), (ActionEvent event) -> {
            if (Main.getScreen() == Screen.GAME) {
                scoreText.setText(Game.getInstance().getScore() + "");
            }
        }));

        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
    }

    @FXML
    public void dragExit(DragEvent event) {
        //this adds grid snapping.
        int x = (int) (GRID_CELL_WIDTH * (Math.floor(Math.abs(event.getX()/ GRID_CELL_WIDTH))));
        int y = (int) (GRID_CELL_HEIGHT * (Math.floor(Math.abs(event.getY()/ GRID_CELL_HEIGHT))));

        int startDragX = Integer.parseInt(event.getDragboard().getString());
        Tool tool = getToolGoingToBeUsed(startDragX);
        if (tool != null) {
            final int tileX = x / GRID_CELL_WIDTH;
            final int tileY = y / GRID_CELL_HEIGHT;
            tool.use(Game.getInstance().getLevel(), new Position(tileX, tileY));
            System.out.println(String.format("You placed a %s at %s relative to the canvas.", tool.getName(), new Position(x, y)));
        }

        event.consume();
    }

    @FXML
    public void dragOver(DragEvent dragEvent)
    {
        if (dragEvent.getGestureSource() == inventory) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
            dragEvent.consume();
        }
    }

    @FXML
    public void dragDetected(MouseEvent mouseEvent) {
        Dragboard db = inventory.startDragAndDrop(TransferMode.ANY);


        ClipboardContent content = new ClipboardContent();
        content.putString(((int) mouseEvent.getX()) + "");

        Tool tool = getToolGoingToBeUsed((int) mouseEvent.getX());
        if (tool != null) {
            content.putImage(tool.getTexture());
        }
        db.setContent(content);

        mouseEvent.consume();
    }

    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent) {
        String playerName = Game.getInstance().getPlayer().getName();
        try {
            GameLoader.saveGame(playerName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will handle the game.
     */
    private void tick()
    {
        Game.getInstance().tick();


        drawLevel(map.getGraphicsContext2D());
        drawInventory(inventory.getGraphicsContext2D());
        drawPopulationBar(populationBar.getGraphicsContext2D());

        timerText.setText(Util.formatTime(Game.getInstance().getLevel().getTime()));
    }

    /**
     * this draws the level on to a canvas.
     * @param gc the canvas to draw on.
     */
    private void drawLevel(GraphicsContext gc) {
        // Clear canvas
        gc.clearRect(0, 0, map.getWidth(), map.getHeight());

        // Set the background to gray.
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, map.getWidth(), map.getHeight());

        //draw level and slimes
        drawLevelVisuals(gc);
        drawSlimes(gc);
        drawPlacedTools(gc);

    }

    /**
     * this puts the level background image on to the canvas.
     *
     * @param gc the canvas to draw on.
     */
    private void drawLevelVisuals(GraphicsContext gc) {
        String levelName = Game.getInstance().getLevelName();
        gc.drawImage(ResourceLoader.getImage("levels/" + levelName + ".png"), 0 ,0);
    }

    /**
     * this draws placed tools on to the level.
     *
     * @param gc the canvas to draw on.
     */
    private void drawPlacedTools(GraphicsContext gc) {
        Level level = Game.getInstance().getLevel();

        for (PlacedTool placedTool : Game.getInstance().getLevel().getPlacedTools()) {
            Tile tile = level.getTileAt(placedTool.getPosition());

            if (!tile.isTunnel()) {
                Image image = placedTool.getTexture();
                Position pos = placedTool.getPosition();
                if (image.getWidth() == GRID_CELL_WIDTH && image.getHeight() == GRID_CELL_HEIGHT) {
                    gc.drawImage(image
                            , pos.getX() * GRID_CELL_WIDTH
                            , pos.getY() * GRID_CELL_HEIGHT);
                } else {
                    double offsetX = Math.floor((image.getWidth() / GRID_CELL_WIDTH) / 2) * GRID_CELL_WIDTH;
                    double offsetY = Math.floor((image.getHeight() / GRID_CELL_HEIGHT) / 2) * GRID_CELL_HEIGHT;
                    System.out.println(offsetX + " " + offsetY);
                    gc.drawImage(image
                            , (pos.getX() * GRID_CELL_WIDTH) - offsetX
                            , (pos.getY() * GRID_CELL_HEIGHT) - offsetY);
                }
            }
        }
    }

    /**
     * this draws the slimes on top the level.
     *
     * @param gc the canvas to draw on.
     */
    private void drawSlimes(GraphicsContext gc) {
        Level level = Game.getInstance().getLevel();
        for (Slime slime : level.getSlimes())
        {
            if (!level.getTileAt(slime.getPosition()).isTunnel()) {
                gc.drawImage(slime.getTexture(), slime.getPosition().getX() * GRID_CELL_WIDTH
                        , slime.getPosition().getY() * GRID_CELL_HEIGHT);

                //drawing breeding heart
                if (slime instanceof WombSlime) {
                    WombSlime wombSlime = (WombSlime) slime;
                    drawSlimeBreedingHeart(gc, wombSlime);
                }
            }
        }
    }

    /**
     * if draws a breeding heart next to a slime if they are breeding.
     *
     * @param gc the canvas to draw on.
     * @param wombSlime the slime to show the heart for.
     */
    private void drawSlimeBreedingHeart(GraphicsContext gc,WombSlime wombSlime) {
        if (wombSlime.isBreeding()) {
            Image breedingHeart = getBreedingHeart(wombSlime.getBreedingTimer());

            if (breedingHeart != null) {
                gc.drawImage(breedingHeart, (wombSlime.getPosition().getX() * GRID_CELL_WIDTH) + 16
                        , (wombSlime.getPosition().getY() * GRID_CELL_HEIGHT) + 16);
            }
        }
    }

    /**
     * this gets an image of the heart depending on how far they are in it breeding.
     *
     * @param breedingTimer an int showing the breeding timer.
     * @return an image object containing the texture for the heart.
     */
    private Image getBreedingHeart(int breedingTimer) {
        Image image = null;
        switch (breedingTimer) {
            case 1:
                image = ResourceLoader.getImage("heart4.png");
                break;
            case 2:
                image = ResourceLoader.getImage("heart3.png");
                break;
            case 3:
                image = ResourceLoader.getImage("heart2.png");
                break;
            case 4:
                image = ResourceLoader.getImage("heart1.png");
                break;
        }
        return image;
    }

    /**
     * this draws the players inventory.
     *
     * @param gc the canvas to draw on.
     */
    private void drawInventory(GraphicsContext gc) {
        gc.clearRect(0, 0, inventory.getWidth(), inventory.getHeight());
        gc.drawImage(ResourceLoader.getImage("boarder.png"), 0, 0, inventory.getWidth(), inventory.getHeight());

        int x = INVENTORY_PADDING;
        for(Tool tool : Game.getInstance().getInventory())
        {
            final int amount = tool.getAmount();
            if(amount > 0)
            {
                drawToolStack(gc, tool, amount, x, INVENTORY_PADDING);
                x += ITEM_ICON_WIDTH_PLUS_PADDING * INVENTORY_SCALE_FACTOR;
            }
        }
    }

    /**
     * this draws an item stack on to the players inventory.
     *
     * @param gc the canvas to draw on.
     * @param tool the tool being drawn.
     * @param amount the amount of the tool.
     * @param x the x cord to draw on.
     * @param y the y cord to draw on.
     */
    private void drawToolStack(GraphicsContext gc, Tool tool, int amount, int x, int y) {
        Image texture = tool.getTexture();
        gc.drawImage(tool.getTexture(), x, y,
                texture.getWidth() * INVENTORY_SCALE_FACTOR, texture.getHeight() * INVENTORY_SCALE_FACTOR);
        if (amount > 1) {
            //TODO remove magic number
            String displayText = amount <= 999 ? String.valueOf(amount) : "∞";

            gc.setFont(Font.font("Arial", FontWeight.NORMAL ,18 * INVENTORY_SCALE_FACTOR));
            gc.setFill(Color.BLACK);
            gc.fillText(displayText, x + 1, inventory.getHeight() - 6);
            gc.setFill(Color.LIGHTGRAY);
            gc.fillText(displayText, x + 2, inventory.getHeight() - 7);
        }
    }

    /**
     * this draws the population bar.
     *
     * @param gc the canvas to draw on.
     */
    private void drawPopulationBar(GraphicsContext gc) {
        gc.clearRect(0, 0, populationBar.getWidth(), populationBar.getHeight());

        double offsetX = 0;
        Level level = Game.getInstance().getLevel();
        Map<Gender,Integer> populations = level.getGenderPopulations();

        gc.setFill(Color.DARKGREY);
        gc.fillRect(0, 0, populationBar.getWidth(), populationBar.getHeight());

        gc.setFill(Color.GREEN);
        double x2 = (int) ((populations.get(Gender.MALE) / (double) level.getPopulationThreshold()) * populationBar.getWidth());
        gc.fillRect(offsetX,0, x2, populationBar.getHeight());
        offsetX += x2;

        gc.setFill(Color.RED);
        x2 = (int) ((populations.get(Gender.FEMALE) / (double) level.getPopulationThreshold()) * populationBar.getWidth());
        gc.fillRect(offsetX,0, x2, populationBar.getHeight());
        offsetX += x2;

        gc.setFill(Color.YELLOW);
        x2 = (int) ((populations.get(Gender.BONUS) / (double) level.getPopulationThreshold()) * populationBar.getWidth());
        gc.fillRect(offsetX,0, x2, populationBar.getHeight());
    }

    /**
     * this method gets a tool that will be used when drag and dropped from the inventory to the map.
     *
     * @param x the intail x position of the drag on the inventory.
     * @return the tool that the player wants to use.
     */
    private Tool getToolGoingToBeUsed(int x)
    {
        List<Tool> inventory = Game.getInstance().getInventory();
        inventory = inventory.stream().filter(tool -> tool.getAmount() > 0).collect(Collectors.toList());

        if(x > ITEM_ICON_WIDTH_PLUS_PADDING * INVENTORY_SCALE_FACTOR * inventory.size()) {
            return null;
        } else {
            int i = (int) (x / (ITEM_ICON_WIDTH_PLUS_PADDING * INVENTORY_SCALE_FACTOR));
            if(i >= inventory.size() || i < 0) {
                return null;
            }

            if(x % ITEM_ICON_WIDTH_PLUS_PADDING * INVENTORY_SCALE_FACTOR < ITEM_ICON_WIDTH * INVENTORY_SCALE_FACTOR) {
                return inventory.get(i);
            } else {
                return null;
            }
        }
    }
}
