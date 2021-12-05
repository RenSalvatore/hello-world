package slimes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import slimes.util.SFX;
import slimes.util.SFXManager;

import java.io.FileNotFoundException;

/**
 *  this handles the components of the Menu Screen.
 *
 * @author Ashley
 */

public class MenuScreen {

    @FXML
    private TextField playerBox;

    @FXML
    private ChoiceBox<String> playerSelectBox;

    @FXML
    private Label motd;

    @FXML
    private WebView youtubeVideo;

    @FXML
    public void initialize() {
        for(String player : Player.getEveryName()) {
            playerSelectBox.getItems().add(player);
        }

        motd.setText(MOTD.getMOTD());
        motd.setRotate(-8);
        System.out.println(motd.getText().length());

        Timeline motdAnimation = new Timeline(new KeyFrame(Duration.millis(50), (ActionEvent event) -> {

            double scale = 1 + Math.sin((double )System.currentTimeMillis() / 200) / 10;
            motd.setScaleX(scale);
            motd.setScaleY(scale);
        }));

        motdAnimation.setCycleCount(Timeline.INDEFINITE);
        motdAnimation.play();
    }

    @FXML
    public void onPlayButtonAction(ActionEvent actionEvent) throws FileNotFoundException {
        String desiredPlayerName = playerSelectBox.getValue();
        if(desiredPlayerName != null) {
            Player player = Player.load(desiredPlayerName);
            Game.getInstance().setPlayer(player);
            Main.switchScreen(Screen.LEVEL_SELECT);
            SFXManager.playSFX(SFX.BUTTON_CLICK);
        }
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        String desiredPlayerName = playerBox.getText();
        SFXManager.playSFX(SFX.BUTTON_CLICK);

        if(!desiredPlayerName.equals("c")) {
            if (Player.isPlayerNameValid(desiredPlayerName)) {
                playerSelectBox.getItems().add(desiredPlayerName);
                playerSelectBox.setValue(desiredPlayerName);
                playerBox.setText("");
            }
        } else {
            youtubeVideo.getEngine().load("https://www.youtube.com/embed/chpT0TzietQ");
            youtubeVideo.setVisible(true);
        }
    }
}
