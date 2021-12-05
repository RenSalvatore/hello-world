package slimes.slime;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.util.ResourceLoader;

/**
 * this class represents a baby slime. baby slimes or faster than adult slimes and they can grow up in to adult slimes.
 *
 * @author Rene
 */

public class BabySlime extends NormalSlime {

    private static final int AGE_TO_BE_AN_ADULT = 11;

    private Gender gender;

    public BabySlime(int x, int y, boolean isSterile, Gender gender) {
        super(x, y, isSterile);
        this.gender = gender;
    }

    public BabySlime(int x, int y, boolean isSterile) {
        super(x, y, isSterile);
        this.gender = Gender.values()[Game.getInstance().getRandom().nextInt(2)];
    }

    /**
     *  This gets the texture of the slime.
	 *
     *  @return an Image Object with the image of the slime.
     */
    public Image getTexture() {
        switch (direction) {
            case UP:
                return ResourceLoader.getImage("baby_slime_up.png");
            case DOWN:
                return ResourceLoader.getImage("baby_slime_down.png");
            case LEFT:
                return ResourceLoader.getImage("baby_slime_right.png");
            case RIGHT:
                return ResourceLoader.getImage("baby_slime_left.png");
        }

        return null;
    }

    /**
     * this handles runs the slimes logic.
     */
    @Override
    public void tick() {
        super.tick();

        if(age >= AGE_TO_BE_AN_ADULT) {
            Level level = Game.getInstance().getLevel();
            if (gender == Gender.MALE) {
                changeToType(level, Gender.MALE);
            } else if (gender == Gender.FEMALE) {
                changeToType(level, Gender.FEMALE);
            } else if (gender == Gender.BONUS) {
                changeToType(level, Gender.BONUS);
            }
        }
    }

    /**
     * this defines the number of points given to the player when this slime is killed.
	 *
     * @return the number of points given to the player.
     */
    @Override
    public int pointsOnKill() {
        return (age >= AGE_TO_BE_AN_ADULT) ? 0 : 10;
    }

    /**
     * this gets the slimes texture.
	 *
     * @return the gender of the slime.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * sets the slimes texture.
	 *
     * @param gender the gender you want to set this slimes gender to.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
