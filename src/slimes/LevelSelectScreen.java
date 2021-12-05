package slimes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

import java.io.FileNotFoundException;
import java.sql.Time;

/**
 * this class handles the components of the Level Select Screen.
 * @author Rene
 */

public class LevelSelectScreen {

    public static final int MAX_NUM_OF_LEVEL = 9;

    @FXML
    public Button levelOneButton;

    @FXML
    public Button levelTwoButton;

    @FXML
    public Button levelThreeButton;

    @FXML
    public Button levelFourButton;

    @FXML
    public Button levelFiveButton;

    @FXML
    public Button levelSixButton;

    @FXML
    public Button levelSevenButton;

    @FXML
    public Button levelEightButton;

    @FXML
    public Button levelNineButton;

    @FXML
    public Button back;

    @FXML
    public Button loadButton;

    @FXML
    public void initialize() {
        applyImage(levelOneButton, "levels/Level1.png");
        applyImage(levelTwoButton, "levels/Level2.png");
        applyImage(levelThreeButton, "levels/Level3.png");
        applyImage(levelFourButton, "levels/Level4.png");
        applyImage(levelFiveButton, "levels/Level5.png");
        applyImage(levelSixButton, "levels/Level6.png");
        applyImage(levelSevenButton, "levels/Level7.png");
        applyImage(levelEightButton, "levels/Level8.png");
        applyImage(levelNineButton, "levels/Level9.png");

        Timeline render = new Timeline(new KeyFrame(Duration.millis(50), (ActionEvent event) -> {
            if(Main.getScreen() == Screen.LEVEL_SELECT) {
                tick();
            }
        }));

        render.setCycleCount(Timeline.INDEFINITE);
        render.play();
    }

    @FXML
    public void buttonOneClicked(ActionEvent actionEvent) {
        loadLevel("Level1");
        SFXManager.playSFX(SFX.BUTTON_CLICK);
    }

    @FXML
    public void buttonTwoClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 2) {
            loadLevel("Level2");
        }
    }

    @FXML
    public void buttonThreeClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 3) {
            loadLevel("Level3");
        }
    }

    @FXML
    public void buttonFourClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 4) {
            loadLevel("Level4");
        }
    }

    @FXML
    public void buttonFiveClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 5) {
            loadLevel("Level5");
        }
    }

    @FXML
    public void buttonSixClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 6) {
            loadLevel("Level6");
        }
    }

    @FXML
    public void buttonSevenClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 7) {
            loadLevel("Level7");
        }
    }

    @FXML
    public void buttonEightClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 8) {
            loadLevel("Level8");
        }
    }

    @FXML
    public void buttonNineClicked(ActionEvent actionEvent) {
        Player player = Game.getInstance().getPlayer();
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        if(player.getCurrentLevel() >= 9) {
            loadLevel("Level9");
        }
    }

    @FXML
    public void loadButtonClick(ActionEvent actionEvent) {
        String playerName = Game.getInstance().getPlayer().getName();

        try {
            GameLoader.loadGame(playerName);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Error: %s does not have a save file", playerName));
        }
    }

    @FXML
    public void OnBackButtonClicked(ActionEvent actionEvent) {
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        Main.switchScreen(Screen.MAIN_MENU);
    }

    /**
     * this loads the level.
     *
     * @param levelName the level you want to load.
     */
    private void loadLevel(String levelName) {
        Game game = Game.getInstance();
        game.setLevel(levelName);
        Main.switchScreen(Screen.GAME);
    }

    /**
     * this puts the image on the button.
     *
     * @param button the button to put the image on.
     * @param imagePath the image path of where the button is.
     */
    private void applyImage(Button button, String imagePath) {
        button.setMaxHeight(200);
        button.setMaxWidth(200);
        Image image = ResourceLoader.getImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(276);
        imageView.setFitHeight(180);

        button.setGraphic(imageView);
    }

    /**
     * this changes whether the image shown is greyscale or not.
     *
     * @param button the button the image is on.
     * @param greyscale a boolen if you want to add greyscale. "true" to add it, false to remove it.
     */
    private void setGreyscale(Button button, boolean greyscale) {
        ImageView imageView = (ImageView) button.getGraphic();

        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(greyscale ? -1 : 0);
        imageView.setEffect(desaturate);

        button.setGraphic(imageView);
    }

    /**
     * this updates all of the level select buttons.
     */
    private void tick() {
        Player player = Game.getInstance().getPlayer();
        if(player != null) {
            loadButton.setVisible(player.hasOngoingGame());

            setGreyscale(levelTwoButton, 2 > player.getCurrentLevel());
            setGreyscale(levelThreeButton, 3 > player.getCurrentLevel());
            setGreyscale(levelFourButton, 4 > player.getCurrentLevel());
            setGreyscale(levelFiveButton, 5 > player.getCurrentLevel());
            setGreyscale(levelSixButton, 6 > player.getCurrentLevel());
            setGreyscale(levelSevenButton, 7 > player.getCurrentLevel());
            setGreyscale(levelEightButton, 8 > player.getCurrentLevel());
            setGreyscale(levelNineButton, 9 > player.getCurrentLevel());
        }
    }
}
