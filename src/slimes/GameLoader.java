package slimes;

import slimes.slime.Slime;
import slimes.tools.PlacedTool;
import slimes.tools.Tool;
import slimes.util.GameLoadSaveUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * a class that handles the saves and loads on going games.
 *
 * @author Rene
 */

public class GameLoader {

    /**
     * this saves an ongoing game.
     *
     * @param fileName the file you want to save the game to.
     * @throws FileNotFoundException
     */
    public static void saveGame(String fileName) throws FileNotFoundException {
        Game game = Game.getInstance();
        File file = new File("data/ongoing_games/" + fileName + ".txt");

        PrintWriter printWriter = new PrintWriter(file);

        printWriter.println(game.getLevelName());
        printWriter.println(GameLoadSaveUtils.saveSlimes(game.getLevel().getSlimes()));
        printWriter.println(GameLoadSaveUtils.savePlacedTools(game.getLevel().getPlacedTools()));
        printWriter.println(GameLoadSaveUtils.saveInventory(game.getInventory()));
        printWriter.println(game.getLevel().getTime());
        printWriter.println(game.getScore());

        printWriter.close();
    }

    /**
     * this loads a game from a file.
     *
     * @param fileName the name of the file you want to save from.
     * @throws FileNotFoundException
     */
    public static void loadGame(String fileName) throws FileNotFoundException {
        File file = new File("data/ongoing_games/" + fileName + ".txt");
        Scanner in = new Scanner(file);

        String levelName = in.nextLine();
        List<Slime> slimes = GameLoadSaveUtils.loadSlimes(new Scanner(in.nextLine()));
        List<PlacedTool> placedTools = GameLoadSaveUtils.loadPlacedTool(new Scanner(in.nextLine()));
        List<Tool> inventory = GameLoadSaveUtils.loadInventory(new Scanner(in.nextLine()));
        int time = in.nextInt();
        int score = in.nextInt();

        Game game = Game.getInstance();
        game.setLevel(levelName);
        game.getLevel().setSlimes(slimes);
        game.getLevel().setPlacedTools(placedTools);
        game.setInventory(inventory);
        game.getLevel().setTime(time);
        game.resetScore();
        game.changeScore(score);

        Main.switchScreen(Screen.GAME);

    }

}
