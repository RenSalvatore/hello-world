package slimes;

/**
 * this class represents a row of the leaderboard.
 *
 * @author Rene
 */

public class LeaderboardEntry {

    private final Player player;
    private final int score;

    public LeaderboardEntry(Player player, int score) {
        this.player = player;
        this.score = score;
    }

    /**
     * this gets the player.
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * this gets the players score.
     * @return thier score.
     */
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "LeaderboardEntry{" +
                "playerName='" + player.getName() + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof LeaderboardEntry) {
            LeaderboardEntry entry = (LeaderboardEntry) object;
            return getPlayer().equals(entry.getPlayer());
        }
        return false;
    }
}
