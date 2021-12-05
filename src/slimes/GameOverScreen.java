package slimes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import slimes.util.SFX;
import slimes.util.SFXManager;
import slimes.util.Util;

import java.text.DecimalFormat;

/**
 * This class handles the components of the game over screen.
 *
 * @author Ashley
 */

public class GameOverScreen {
    @FXML
    public Label statusText;

    @FXML
    public Label scoreText;

    @FXML
    public Label timeText;

    @FXML
    public Canvas leaderboard;

    @FXML
    public Button nextLevelButton;

    private LeaderboardDisplay leaderboardDisplay;

    @FXML
    public void initialize() {
        leaderboardDisplay = new LeaderboardDisplay(leaderboard);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), (ActionEvent event) -> {
            if (Main.getScreen() == Screen.GAME_OVER) {
                tick();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * This updates everything that needs to be drawn on the game.
     */
    private void tick() {
        Game game = Game.getInstance();
        Level level = game.getLevel();

        if (game.getStatus() == Game.Status.WON) {
            statusText.setText("You won!");
        } else if (game.getStatus() == Game.Status.LOST) {
            statusText.setText("You lost!");
        }

        scoreText.setText(String.format("You scored %s", game.getScore() + ""));

        int gameLength = level.getParTime() - level.getTime();
        timeText.setText("Time: " + Util.formatTime(gameLength));

        if (Game.getInstance().getPlayer() != null) {
            Player player = Game.getInstance().getPlayer();
            int playerRank = Game.getInstance().getLeaderboard().getRank(player);
            leaderboardDisplay.setHilightedRank(playerRank);
        }
        leaderboardDisplay.drawLeaderboard();

        nextLevelButton.setVisible(level.getLevelHigheracy() < LevelSelectScreen.MAX_NUM_OF_LEVEL);
    }

    @FXML
    public void onMainMenuClick(ActionEvent actionEvent) {
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        Main.switchScreen(Screen.MAIN_MENU);
    }

    @FXML
    public void onRetryButtonClicked(ActionEvent actionEvent) {
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        String levelName = Game.getInstance().getLevelName();
        newGame(levelName);
    }

    @FXML
    public void onLeaderboardScroll(ScrollEvent scrollEvent) {
        leaderboardDisplay.changeScrollY((int) scrollEvent.getDeltaY() * -1);
    }

    @FXML
    public void onNextLevelButtonClicked(ActionEvent actionEvent) {
        SFXManager.playSFX(SFX.BUTTON_CLICK);
        Level level = Game.getInstance().getLevel();
        if(level.getLevelHigheracy() < LevelSelectScreen.MAX_NUM_OF_LEVEL ) {
            newGame("Level" + (level.getLevelHigheracy() + 1));
        }
    }

    /**
     * this starts a new game on the desired level.
     *
     * @param levelName the name of the level the player wants to play.
     */
    private void newGame(String levelName) {
        Player player = Game.getInstance().getPlayer();
        Game game = new Game();
        game.setLevel(levelName);
        game.setPlayer(player);
        Main.switchScreen(Screen.GAME);
    }
}
