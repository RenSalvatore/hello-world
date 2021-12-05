package slimes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * this class represents a leaderboard.
 * @author rene
 */

public class Leaderboard {

    private List<LeaderboardEntry> leaderboard;
    private String levelName;

    public Leaderboard(String levelName) {
        try {
            leaderboard = loadLeaderboard(levelName);
            this.levelName = levelName;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * this gets a entry on the leaderboard.
     *
     * @param rank the rank of the player you want to get (starts at 1 not 0).
     * @return a leaderboard entry object.
     */
    public LeaderboardEntry getEntry(int rank) {
        return leaderboard.get(rank - 1);
    }

    /**
     * this gets the rank of a player on the leaderboard.
     * this returns -1 if the player is not on the leaderboard.
     *
     * @param player the player.
     * @return the rank of the player.
     */
    public int getRank(Player player) {
        int rank = 1;
        for (LeaderboardEntry entry : leaderboard) {
            if (entry.getPlayer().equals(player)) {
                return rank;
            } else {
                rank++;
            }
        }
        return -1;
    }

    /**
     * this gets the amount of players on the leaderboard.
     *
     * @return the number of players on the leaderboard.
     */
    public int getSize() {
        return leaderboard.size();
    }

    /**
     * this saves a score to the leaderboard.
     * only the highest score the player achives gets put in the leaderboard.
     *
     * @param player the player.
     * @param score the players score.
     */
    public void updateScore(Player player, int score) {
        int currentRank = getRank(player);

        //if the player is already listed in the leaderboards
        if(getRank(player) != -1) {
            if(score < getEntry(currentRank).getScore()) {
                //only the hoghest score the player has ever gotten gets saved to the leaderboard
                return;
            }

            leaderboard.remove(currentRank - 1);
        }

        leaderboard.add(new LeaderboardEntry(player, score));
        sort(leaderboard);
    }

    /**
     * this saves the leaderboard to a file
     */
    public void saveToFile() {
        File file = new File("data/scores/" + levelName + ".txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for(LeaderboardEntry entry : leaderboard) {
            printWriter.println(entry.getScore() + "," + entry.getPlayer().getName());
        }
        printWriter.close();
    }

    @Override
    public String toString() {
        return leaderboard.toString();
    }

    /**
     * this loads the leaderboard from a file
     *
     * @param levelName the level you want to load a leaderboard for
     * @return a list of leaderboard entries
     */
    private List<LeaderboardEntry> loadLeaderboard(String levelName) throws IOException {
        List<LeaderboardEntry> output = new ArrayList<LeaderboardEntry>();

        if(!Level.doesLevelExist(levelName)) {
            throw new IllegalArgumentException(String.format("Level %s does not exist!", levelName));
        }

        File file = new File("data/scores/" + levelName + ".txt");
        if(!file.exists()) {
            file.createNewFile();
        }

        Scanner in = new Scanner(file);

        while(in.hasNextLine()) {
            String line = in.nextLine();

            int score = Integer.parseInt(line.split(",")[0]);
            String playerName = line.split(",")[1];
            Player player = Player.load(playerName);

            if(score >= 0) {
                LeaderboardEntry entry = new LeaderboardEntry(player, score);
                output.add(entry);
            }
        }

        sort(output);

        return output;
    }

    /**
     * this sorts the leaderboard so the player with the most score is at the top
     * @param leaderboard
     */
    private static void sort(List<LeaderboardEntry> leaderboard) {
        leaderboard.sort((entry1, entry2) -> {
            if (entry1.getScore() > entry2.getScore()) {
                return -1;
            } else if (entry1.getScore() < entry2.getScore()) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}
