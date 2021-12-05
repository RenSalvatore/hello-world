package slimes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import slimes.slime.Gender;
import slimes.tools.*;
import slimes.util.SFX;
import slimes.util.SFXManager;

import java.util.*;

/**
    This class handles the game logic.

    @author Rene
 */

public class Game
{
    private static final int VICTORY_BONUS = 50;

    private static Game instance;

    private Level level;
    private List<Tool> inventory;
    private final Random rng;
    private int score;
    private Status status;
    private Leaderboard leaderboard;
    private Player player;
    private String levelName;

    public Game() {
        instance = this;
        rng = new Random();
        score = 0;
        status = Status.ON_GOING;

        inventory = new ArrayList<>();
        inventory.add(new Bomb(0));
        inventory.add(new GasTool(0));
        inventory.add(new SterilisationTool(0));
        inventory.add(new PoisonTool(0));
        inventory.add(new NoEntryTool(0));
        inventory.add(new DeathSlimeTool(0));
        inventory.add(new SexChange(0, Gender.MALE));
        inventory.add(new SexChange(0, Gender.FEMALE));
        inventory.add(new SexChange(0, Gender.BONUS));
    }

    public static Game getInstance() {
        return instance;
    }

    /**
     *  This handles the game logic.
     */
    public void tick() {
        level.tick();

        if(level.getPopulation() == 0) {
            status = Status.WON;
            System.out.println("The player won the game.");
            score += VICTORY_BONUS;
            SFXManager.playSFX(SFX.WIN);
            if(level.getLevelHigheracy() == player.getCurrentLevel()) {
                player.unlockNextLevel();
                player.save();
            }
            endGame();
        } else if(level.getPopulation() >= level.getPopulationThreshold()) {
            gameOver();
        }
    }

    /**
     * End the game when the player loses.
     */
    public void gameOver(){
        status = Status.LOST;
        System.out.println("The player lost the game.");
        SFXManager.playSFX(SFX.LOSE);
        endGame();
    }

    /**
     * adds tools to the players inventory.
     *
     * @param targetToolName the tools number (all lower case and with underscores as spaces).
     * @param amount the amount of the tool you want to give.
     */
    public void giveItem(String targetToolName, int amount) {
        for(Tool tool : inventory) {
            String toolName = tool.getName().toLowerCase().replace(' ','_');
            if(targetToolName.equals(toolName)) {
                tool.give(amount);
            }
        }
    }

    /**
     * this gets the level that is currently being played.
     *
     * @return a Level object of the level being played.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * this sets and loads the level.
     *
     * @param levelName the name of the level to load.
     */
    public void setLevel(String levelName) {
        this.level = Level.loadLevel(levelName);
        this.leaderboard = new Leaderboard(levelName);
        this.levelName = levelName;
    }

    /**
     * This gets the random seed.
     *
     * @return the random seed.
     */
    public Random getRandom() {
        return rng;
    }

    /**
     * this gets the players inventory.
     *
     * @return the players inventory.
     */
    public List<Tool> getInventory() {
        return inventory;
    }

    /**
     * this gets the players score.
     *
     * @return the players score.
     */
    public int getScore() {
        return score;
    }

    /**
     * this sets the players score to 0.
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * this changes the players score by the value given.
     *
     * @param amount the amount you want to change the players score by.
     */
    public void changeScore(int amount) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50),
                (ActionEvent event) -> score += amount / 10));

        timeline.setCycleCount(10);
        timeline.play();
    }

    /**
     * this gets the status of the game of whether is has been won, lost or still in progress.
     *
     * @return the status of the game.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * the gets the leaderboard of the level being played.
     * @return the leaderboard of the level being played.
     */
    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    /**
     * this gets the player playing the level.
     *
     * @return the player playing the level.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * this changes the player that is going to change the game.
     *
     * @param player the player you want to be playing the game.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * this gets the name of the level being played.
     *
     * @return the name of the level being played.
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * this sets the players inventory .
     *
     * @param inventory the inventory you want that player to have.
     */
    protected void setInventory(List<Tool> inventory) {
        this.inventory = inventory;
    }

    /**
     * this ends the game and puts the player on the game over screen.
     */
    private void endGame() {
        //bonus points for finishing the level quickly
        if(level.getTime() > 0) {
            score += level.getTime();
        }

        leaderboard.updateScore(player, score);
        leaderboard.saveToFile();
        Main.switchScreen(Screen.GAME_OVER);
    }

    /**
     * this defines the current state of the game.
     */
    public enum Status {
        WON,
        LOST,
        ON_GOING
    }
}
