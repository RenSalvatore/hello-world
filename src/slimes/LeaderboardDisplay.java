package slimes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import slimes.util.Util;

/**
 * this class draws out a leaderboard on a canvas so the scores are visable to the player.
 *
 * @author Rene
 */

public class LeaderboardDisplay {

    private static final Font LEADERBOARD_FONT = Font.font("Arial", FontWeight.NORMAL ,24);
    private static final int ROW_HEIGHT = 32;

    private Canvas leaderboardCanvas;
    private int scrollY;
    private int hilightedRank;

    public LeaderboardDisplay(Canvas canvas) {
        this.leaderboardCanvas = canvas;
        this.scrollY = 0;
        this.hilightedRank = -1;
    }

    /**
     * this draws the leaderboard.
     */
    public void drawLeaderboard() {
        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        final int start = (scrollY / ROW_HEIGHT) + 1;
        final int end = (start) + getMaxRows();

        for (int rank = start; rank <= end; rank++) {
            drawRow(rank);
        }
    }

    /**
     * this changes the scroll Y by an amount.
     * @param amount to change the Scroll Y by.
     */
    public void changeScrollY(int amount) {
        scrollY += amount;
        if (scrollY < 0) {
            scrollY = 0;
        }
        if (scrollY > getMaxScrollY()) {
            scrollY = getMaxScrollY();
        }
    }

    /**
     * this allows you to set the highlighted row of the leaderboard.
     * @param highlightedRank he highlighted row of the leaderboard you want to set.
     */
    public void setHilightedRank(int highlightedRank) {
        this.hilightedRank = highlightedRank;
    }

    /**
     * this draws a row of the leaderboard on to the canvas.
     * @param rank the rank you want to draw.
     */
    private void drawRow(int rank) {
        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        Leaderboard leaderbord = Game.getInstance().getLeaderboard();
        if (rank > leaderbord.getSize() || rank <= 0) {
            return;
        }

        LeaderboardEntry entry = leaderbord.getEntry(rank);
        int x = (ROW_HEIGHT * (rank - 1)) - scrollY;

        gc.setFill(getRowColour(rank));
        gc.fillRect(0 , x, gc.getCanvas().getWidth(), ROW_HEIGHT);
        gc.setFill(Color.BLACK);
        gc.setFont(LEADERBOARD_FONT);
        gc.fillText(
                String.format("%s %s %s", Util.toOrdinalNumber(rank), entry.getPlayer().getName(), entry.getScore())
                ,4, x + LEADERBOARD_FONT.getSize());
    }

    /**
     * this gets the colour of the row based on the rank.
     *
     * @param rank the rank.
     * @return a colour abject.
     */
    private  Color getRowColour(int rank) {
        if(hilightedRank == rank) {
            return Color.valueOf("rgb(126, 200, 158)");
        }

        if (rank == 1) {
            return Color.GOLD;
        } else if (rank == 2) {
            return Color.SILVER;
        } else if (rank == 3) {
            return Color.BROWN;
        } else if (rank % 2 == 0) {
            return Color.LIGHTBLUE;
        } else {
            return Color.valueOf("rgb(121, 181, 241)");
        }
    }

    /**
     * this gets the max rows that can be shown on the screen at once.
     *
     * @return the max rows that can be shown on the screen at onc.
     */
    private int getMaxRows() {
        double height = leaderboardCanvas.getHeight();
        return (int) Math.ceil(height / (double) ROW_HEIGHT);
    }

    /**
     * this gets the maximum scrollY you could scroll down to on this leaderboard.
     * @return the maximum scrollY you could scroll down to on this leaderboard.
     */
    private int getMaxScrollY() {
        Leaderboard leaderbord = Game.getInstance().getLeaderboard();

        int totalSize = leaderbord.getSize() * ROW_HEIGHT;
        int maxScrollY = totalSize - (int) leaderboardCanvas.getHeight();

        return Math.max(maxScrollY, 0);
    }
}
