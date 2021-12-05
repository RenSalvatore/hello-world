package slimes.slime;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.util.Direction;
import slimes.util.Position;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  A class that defines behaviour for slimes.
 * @author Rene
 */

abstract public class Slime {

    protected Position position;
    protected Direction direction;
    protected int age;


    public Slime(int x, int y) {
        this.position = new Position(x, y);
        age = 0;
        direction = Direction.values()[Game.getInstance().getRandom().nextInt(4)];
    }

    /**
     * this gets the position of the slime.
     * @return the current position of the slime.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * this sets the position of the slime.
     * @param position the position you want the slime to be.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * this gets the direction the slime is facing.
     * @return the direction the slime is facing.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * sets the direction the player is facing.
     * @param direction the direction you want the slime to face.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *  This gets the texture of the slime.
     *  @return an Image Object with the image of the slime.
     */
    abstract public Image getTexture();

    /**
     * this defines the number of points given to the player when this slime is killed.
     * @return the number of points given to the player.
     */
    abstract public int pointsOnKill();

    /**
     * this handles the slimes logic.
     */
    public void tick() {
        age++;
        move();
    }

    /**
     * this gets the possible directions the slime could go from its current position.
     * @return a List of Directions which are the possible directions the slime could go from its current position.
     */
    private List<Direction> getPossibleDirections() {
        Level level = Game.getInstance().getLevel();
        List validDirections =  Arrays.stream(Direction.values())
                .filter((dir) -> level.isValidPosition(Position.getCordsIn(dir, position)))
                .collect(Collectors.toList());

        //if there are more options for the slime to go the slime will never go back on themself.
        if (validDirections.size() >= 2 && direction != null)
        {
            validDirections.remove(direction.getBackwards());
        }

        return validDirections;
    }

    /**
     * this method moves the slime by one space.
     */
    protected void move() {
        Level level = Game.getInstance().getLevel();

        List<Direction> directions = getPossibleDirections();
        if (direction == null) {
            int i = Game.getInstance().getRandom().nextInt(directions.size());
            direction = directions.get(i);
        }

        if (directions.size() >= 2) {
            int i = Game.getInstance().getRandom().nextInt(directions.size());
            direction = directions.get(i);
        }

        if (!level.isValidPosition(Position.getCordsIn(direction, position))) {
            int i = Game.getInstance().getRandom().nextInt(directions.size());
            direction = directions.get(i);
        }
        position = Position.getCordsIn(direction, position);
    }
}
