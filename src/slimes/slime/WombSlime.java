package slimes.slime;

import slimes.Game;
import slimes.util.Direction;
import slimes.util.Position;

/**
 *  this class defines behaviour for a slime that can give birth.
 * @author Rene
 */

public abstract class WombSlime extends AdultSlime {

    private static final int MAX_TIME_PREGNANT = 5;
    private static final int BREEDING_TIME = 5;

    protected boolean isPregnant;
    private int pregnantTimer;
    protected int breedingTimer;
    protected AdultSlime breedingPartner;

    public WombSlime(int x, int y, Direction direction, boolean isSterile) {
        super(x, y, direction, isSterile);

        this.isPregnant = false;
        this.pregnantTimer = 0;
        this.breedingTimer = 0;
    }

    /**
     * this runs the slimes logic.
     */
    @Override
    public void tick() {
        super.tick();

        if (isBreeding) {
            breedingTimer++;
            if (breedingTimer >= BREEDING_TIME) {
                isPregnant = true;

                this.isBreeding = false;
                if (breedingPartner != null) {
                    breedingPartner.isBreeding = false;
                    breedingPartner = null;
                }

                breedingTimer = 0;
            }
        }

        if (isPregnant) {
            pregnantTimer++;
            if(pregnantTimer >= MAX_TIME_PREGNANT) {
                giveBirth();
            }
        }
    }

    /**
     *  this creates a new baby slime on the level with a random gender.
     */
    public void giveBirth() {
        Game.getInstance().getLevel().addSlime(new BabySlime(position.getX(), position.getY(), false));
        pregnantTimer = 0;
        isPregnant = false;
    }

    /**
     * @return whether the slime is able to breed.
     */
    @Override
    public boolean canBreed() {
        return super.canBreed() && !isPregnant;
    }

    /**
     * this gets the time left the slimes will be breeding for.
     * @return time left the slimes will be breeding for.
     */
    public int getBreedingTimer() {
        return breedingTimer;
    }
}
