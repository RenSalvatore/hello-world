package slimes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import slimes.util.ResourceLoader;
import slimes.util.SFXManager;

import java.io.IOException;

/**
 * The main class of the program. Launches the javaFX applications
 *
 * @author Liam O'Reilly
 * @author Rene
 */
public class Main extends Application
{
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 1366;
    private static final int WINDOW_HEIGHT = 768;

    private static Screen screen;
    private static Stage primaryStage;

    private static Scene gameScene = null;
    private static Scene mainMenuScene = null;
    private static Scene gameOverScene = null;
    private static Scene levelSelectScene = null;
    private static Scene highscoreScene = null;

    private static Game game;
    private static final SFXManager SFX_MANAGER = new SFXManager();


    /**
     * Set up the new application.
     * @param primaryStage The stage that is to be used for the application.
     */
    public void start(Stage primaryStage){
        Main.primaryStage = primaryStage;

        primaryStage.setTitle("Slimes");
        primaryStage.getIcons().add(ResourceLoader.getImage("slime_icon_placeholder.png"));

        gameScene = loadScene(gameScene, "game.fxml");
        mainMenuScene = loadScene(mainMenuScene, "menu.fxml");
        gameOverScene = loadScene(gameOverScene, "game_over.fxml");
        levelSelectScene = loadScene(levelSelectScene, "level_select.fxml");
        highscoreScene = loadScene(highscoreScene, "highscores.fxml");

        game = new Game();

        // Display the scene on the stage
        switchScreen(Screen.MAIN_MENU);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * gets a screen that the player is currently on.
     *
     * @return a Screen object enum.
     */
    public static Screen getScreen()
    {
        return screen;
    }

    /**
     *  switches the screen that the player is currently on.
     *
     * @param screen the screen you want to show to the player.
     */
    public static void switchScreen(Screen screen)
    {
        switch (screen) {
            case GAME:
                primaryStage.setScene(gameScene);
                SFX_MANAGER.playLevelMusic();
                break;
        case MAIN_MENU:
                primaryStage.setScene(mainMenuScene);
                SFX_MANAGER.playMenuMusic();
                break;
        case GAME_OVER:
                primaryStage.setScene(gameOverScene);
                SFX_MANAGER.playMenuMusic();
                break;
        case LEVEL_SELECT:
                primaryStage.setScene(levelSelectScene);
                break;
        case HIGH_SCORE:
                primaryStage.setScene(highscoreScene);
                break;
        }

        Main.screen = screen;
    }

    /**
     * the entry point of the program.
     *
     * @param args the program arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * this loads a scene.
     *
     * @param scene the scene object.
     * @param path the path to the fxml.
     * @return the initialised scene object.
     */
    private Scene loadScene(Scene scene, String path) {
        try {
            scene = new Scene(ResourceLoader.getFXML(path).load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.getStylesheets().add(ResourceLoader.getCSS("styles.css"));
        return scene;
    }
}