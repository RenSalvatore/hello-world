package slimes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents a player and stores data that relevant for that player.
 * @author Rene
 */

public class Player {

    public static final int MAX_NAME_LENGTH = 16;

    private final String name;

    private int currentLevel;

    private Player(String name) {
        this.name = name;
    }

    /**
     * this gets if the chosen player name valid.
     *
     * @param playerName the desired player name.
     * @return whether if the player name is valid.
     */
    public static boolean isPlayerNameValid(String playerName) {
        if(playerName.length() > MAX_NAME_LENGTH || playerName.length() == 0) {
            return false;
        } else if (playerName.contains(",")) {
            return false;
        } else if(nameTaken(playerName)) {
            return false;
        } else if(playerName.length() > 8) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * this gets the players name.
     *
     * @return the players name.
     */
    public String getName() {
        return name;
    }

    /**
     * this loads a player
     *
     * @param playerName the name of the player you want to load.
     * @return the player that got loaded.
     * @throws FileNotFoundException
     */
    public static Player load(String playerName) throws FileNotFoundException {
        if(!playerExists(playerName)) {
            initPlayer(playerName);
        }

        Player player = new Player(playerName);
        File file = new File("data/players/" + playerName + ".txt");
        Scanner in = new Scanner(file);
        player.currentLevel = in.nextInt();

        return player;
    }

    /**
     * this gets a list of players names that have been created.
     *
     * @return a list of players names that have been created.
     */
    public static List<String> getEveryName() {
        File playerDir = new File("data/players/");
        File[] playerFiles = playerDir.listFiles();
        if(playerFiles != null) {
            List<String> names = Arrays.stream(playerFiles)
                    .map(file -> file.getName().substring(0, file.getName().length() - 4))
                    .collect(Collectors.toList());
            return names;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * this gets if the desired players name has been taken.
     *
     * @param playerName the desired players name.
     * @return a boolean value whether if the name has been taken.
     */
    private static boolean nameTaken(String playerName) {
        List<String> names = getEveryName();
        return  names.contains(playerName);
    }

    /**
     * this gets if this player has an on going game saved.
     *
     * @return true if a player has an on going game saved, false otherwise.
     */
    public boolean hasOngoingGame() {
        File file = new File("data/ongoing_games/" + name + ".txt");
        return file.exists();
    }

    /**
     * this unlocks the next level for the player
     */
    public void unlockNextLevel() {
        currentLevel++;
    }

    /**
     * this writes the players data in to a file
     */
    public void save() {
        File file = new File("data/players/" + name + ".txt");

        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        printWriter.println(currentLevel);
        printWriter.close();
    }

    /**
     * this gets the current level the player is on.
     *
     * @return the current level the player is on.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * this gets whether the player has been created.
     *
     * @return true if the player has been created, false otherwise.
     */
    private static boolean playerExists(String playerName) {
        File file = new File("data/players/" + playerName + ".txt");
        return file.exists();
    }

    /**
     * this creates a file for a new player. if it doesnt exist.
     *
     * @param playerName the new player you want to make the file for.
     */
    private static void initPlayer(String playerName) {
        File file = new File("data/players/" + playerName + ".txt");

        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        printWriter.println("1");
        printWriter.close();
    }
}
