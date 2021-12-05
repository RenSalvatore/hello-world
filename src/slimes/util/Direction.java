package slimes.util;

/**
 * This gets the possible directions the slimes can face.
 *
 * @author Rene
 */

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * this gets the opposite direction of a direction.
     * @return the opposite direction of the direction.
     */
    public Direction getBackwards() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }

        return null;
    }
}
