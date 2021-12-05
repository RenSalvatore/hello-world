package slimes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;
import slimes.util.Util;

/**
 * this class handles the components of the highscore screen.
 * @author Rene
 */

public class HighscoreScreen {
    @FXML
    public Canvas leaderboard;

    private LeaderboardDisplay leaderboardDisplay;

    @FXML
    public void initialize() {
        leaderboardDisplay = new LeaderboardDisplay(leaderboard);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), (ActionEvent event) -> {
            if (Main.getScreen() == Screen.HIGH_SCORE) {
                tick();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * this method is called every tick to update the screen.
     */
    private void tick() {
        Game game = Game.getInstance();

        if(game.getPlayer() != null) {
            Player player = game.getPlayer();
            int playerRank = game.getLeaderboard().getRank(player);
            leaderboardDisplay.setHilightedRank(playerRank);
        }
        leaderboardDisplay.drawLeaderboard();
    }

    @FXML
    public void onLeaderboardScroll(ScrollEvent scrollEvent) {
        leaderboardDisplay.changeScrollY((int) scrollEvent.getDeltaY() * -1);
    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) {
        Main.switchScreen(Screen.MAIN_MENU);
    }
}
