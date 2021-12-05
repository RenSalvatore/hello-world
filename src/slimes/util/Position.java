package slimes.util;

/**
 * A class that represents coordinates (x,y).
 * @author Rene
 */

public class Position
{
    private int x;
    private int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * gets the coordinates if you went in that direction.
     *
     * @param direction the direction you want to go in.
     * @param pos the starting position.
     * @return the new position.
     */
    public static Position getCordsIn(Direction direction, Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();

        switch (direction)
        {
            case UP:
                return new Position(x, y - 1);
            case LEFT:
                return new Position(x + 1, y);
            case DOWN:
                return new Position(x, y + 1);
            case RIGHT:
                return new Position(x - 1, y);
        }

        return new Position(x, y);
    }

    /**
     * This gets the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * This sets the x coordinate.
     * @param x the x coordinate you want to set it to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * This gets the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * This sets the y coordinate.
     * @param y the y coordinate you want to set it to.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * this gets the distance between two points.
     *
     * @param pos2 the other point you want to know the position to.
     * @return a number telling how far apart these two points are.
     */
    public int getDistance(Position pos2) {
        double distance = Math.sqrt(Math.pow(this.x - pos2.x, 2) + Math.pow(this.y - pos2.y, 2));
        return (int) distance;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x + "", y + "");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position pos2 = (Position) obj;
            return pos2.getX() == x && pos2.getY() == y;
        }
        return false;
    }
}
