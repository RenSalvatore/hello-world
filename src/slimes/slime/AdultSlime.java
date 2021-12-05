package slimes.slime;

import slimes.Game;
import slimes.Level;
import slimes.util.Direction;

/**
 * A class that defines behaviour for adult slimes.
 * @author Rene
 */

abstract public class AdultSlime extends NormalSlime {

    protected boolean isBreeding;

    public AdultSlime(int x, int y, Direction direction, boolean isSterile) {
        super(x, y, isSterile);
        this.direction = direction;
        isBreeding = false;
    }

    /**
     * @return whether the slime is able to breed.
     */
    public boolean canBreed() {
        return !isSterile && !isBreeding;
    }

    public void setGender(Gender gender) {
        Level level = Game.getInstance().getLevel();
        super.changeToType(level, gender);
    }

    /**
     * this gets whether the slime is currently breeding.
     * @return whether the slime is currently breeding.
     */
    public boolean isBreeding() {
        return isBreeding;
    }

    /**
     * this handles the slimes logic.
     */
    @Override
    public void tick() {
        if (!isBreeding) {
            move();
        }
        age++;
    }

    /**
     * @param slime that this slime wants to breed with.
     * @return whether they can breed or not.
     */
    public abstract boolean canBreedWith(AdultSlime slime);
}
