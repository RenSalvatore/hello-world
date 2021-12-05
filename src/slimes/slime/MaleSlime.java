package slimes.slime;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.util.Direction;
import slimes.util.Position;
import slimes.util.ResourceLoader;

/**
 * this class represents a male slime.
 * @author Rene
 */

public class MaleSlime extends AdultSlime {

    public MaleSlime(int x, int y, Direction direction, boolean isSterile) {
        super(x, y, direction, isSterile);
    }

    /**
     * @param slime that this slime wants to breed with.
     * @return whether they can breed or not.
     */
    @Override
    public boolean canBreedWith(AdultSlime slime) {
        return slime.getGender() != getGender();
    }

    /**
     *  This gets the texture of the slime.
     *  @return an Image Object with the image of the slime.
     */
    public Image getTexture() {
        switch (direction) {
            case UP:
                return ResourceLoader.getImage("male_slime_up.png");
            case DOWN:
                return ResourceLoader.getImage("male_slime_down.png");
            case LEFT:
                return ResourceLoader.getImage("male_slime_right.png");
            case RIGHT:
                return ResourceLoader.getImage("male_slime_left.png");
        }

        return null;
    }

    /**
     * this defines the number of points given to the player when this slime is killed.
     * @return the number of points given to the player.
     */
    @Override
    public int pointsOnKill() {
        return 10;
    }

    /**
     * this gets the slimes gender.
     * @return the slimes gender.
     */
    @Override
    public Gender getGender() {
        return Gender.MALE;
    }
}
