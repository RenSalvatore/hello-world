package slimes.slime;

import javafx.scene.image.Image;
import slimes.util.Direction;
import slimes.util.ResourceLoader;

/**
 * this class represents a female slime.
 * @author Rene
 */

public class FemaleSlime extends WombSlime {

    public FemaleSlime(int x, int y, Direction direction, boolean isSterile) {
        super(x, y, direction, isSterile);
    }

    /**
     *  This gets the texture of the slime.
     *  @return an Image Object with the image of the slime.
     */
    public Image getTexture() {
        switch (direction) {
            case UP:
                return ResourceLoader.getImage("female_slime_up.png");
            case DOWN:
                return ResourceLoader.getImage("female_slime_down.png");
            case LEFT:
                return ResourceLoader.getImage("female_slime_right.png");
            case RIGHT:
                return ResourceLoader.getImage("female_slime_left.png");
        }

        return null;
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
     * this defines the number of points given to the player when this slime is killed.
     * @return the number of points given to the player.
     */
    @Override
    public int pointsOnKill() {
        return 10;
    }

    /**
     * the gets the gender of the slime.
     * @return the gender of the slime.
     */
    @Override
    public Gender getGender() {
        return Gender.FEMALE;
    }
}
