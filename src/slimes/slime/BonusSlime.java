package slimes.slime;

import javafx.scene.image.Image;
import slimes.util.*;

/**
 * This class represents a bonus slime a bonus slime can breed with any gender desinged to make the game more difficult.
 * @author Rene
 */

public class BonusSlime extends WombSlime {

    public BonusSlime(int x, int y, Direction direction, boolean isSterile) {
        super(x, y, direction, isSterile);
        SFXManager.playSFX(SFX.BONUS_SLIME);
    }

    /**
     * @param slime that this slime wants to breed with.
     * @return whether they can breed or not.
     */
    @Override
    public boolean canBreedWith(AdultSlime slime) {
        // bonus slimes can bread with any slime
        return true;
    }

    /**
     * the gets the gender of the slime.
     * @return the gender of the slime.
     */
    @Override
    public Gender getGender() {
        return Gender.BONUS;
    }

    /**
     *  This gets the texture of the slime.
     *  @return an Image Object with the image of the slime.
     */
    @Override
    public Image getTexture() {
        switch (direction) {
            case UP:
                return ResourceLoader.getImage("bonus_slime_up.png");
            case DOWN:
                return ResourceLoader.getImage("bonus_slime_down.png");
            case LEFT:
                return ResourceLoader.getImage("bonus_slime_right.png");
            case RIGHT:
                return ResourceLoader.getImage("bonus_slime_left.png");
        }

        return null;
    }

    /**
     * this defines the number of points given to the player when this slime is killed.
     * @return the number of points given to the player.
     */
    @Override
    public int pointsOnKill() {
        return 25;
    }


}
