package slimes.slime;

import slimes.Level;

/**
 * a class the helps define behaviour for slimes that grow up and have a gender.
 * @author Rene
 */

abstract public class NormalSlime extends Slime {

    /**
     *  if this is true then this slime cant breed.
     */
    protected boolean isSterile;

    public NormalSlime(int x, int y, boolean isSterile) {
        super(x, y);
        this.isSterile = isSterile;
    }

    /**
     * this gets whether the slime is sterile or not.
     * @return whether the slime is sterile.
     */
    public boolean isSterile() {
        return isSterile;
    }

    /**
     * this makes this slime sterile and there for can not breed.
     */
    public void becomeSterile() {
        isSterile = true;
    }

    /**
     * this gets the gender of the slime.
     * @return the gender of this slime.
     */
    abstract public Gender getGender();

    /**
     * sets the slimes texture.
     * @param gender the gender you want to set this slimes gender to.
     */
    abstract public void setGender(Gender gender);

    /**
     * this changes the slimes type.
     *
     * @param level the level the slime is on.
     * @param type the type you want to change it to.
     */
    protected void changeToType(Level level, Gender type) {
        boolean success = false;

        switch (type) {
            case MALE:
                level.addSlime(new MaleSlime(position.getX(), position.getY(), direction, isSterile));
                success = true;
                break;
            case FEMALE:
                level.addSlime(new FemaleSlime(position.getX(), position.getY(), direction, isSterile));
                success = true;
                break;
            case BONUS:
                level.addSlime(new BonusSlime(position.getX(), position.getY(), direction, isSterile));
                success = true;
                break;
        }

        if (success) {
            level.killSlimes(this, false);
        }
    }
}
