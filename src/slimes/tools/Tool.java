package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.util.Position;

/**
 * this defines the basic behaviour of a tool.
 * @author Rene
 */

public abstract class Tool {

    public static final int MAX_STACK = 4;

    protected int amount;

    public Tool(int amount) {
        this.amount = amount;
    }

    public Tool() {
        this.amount = 0;
    }

    /**
     * this gives you more of a tool.
     *
     * @param amount the amount you want to give.
     */
    public void give(int amount) {
        this.amount += Math.max(0, Math.min(amount, MAX_STACK - this.amount));
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    /**
     * this gets the amount of this tool that is currently available.
     *
     * @return the amount of this tool that is currently available.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * this gets the amount of the tool that is available.
     * the value you set here can go over the max stack.
     *
     * @param amount the amount of this tool that you want to set this to.
     */
    public void setAmount(int amount) {
        this.amount = amount;
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    /**
     * this uses up the tool.
     *
     * @param level the level that the tool is in.
     * @param pos the position of the tool being used.
     */
    public void use(Level level, Position pos) {
        if (amount > 0) {
            final boolean success = onUse(level, pos);
            if (success) {
                amount--;
            }
        }
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    abstract public Image getTexture();

    /**
     * this function gets executed when the item is used.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether if it was successful or not.
     */
    abstract protected boolean onUse(Level level, Position pos);

    /**
     * this function returns the name of the tool.
     *
     * @return the name of the tool.
     */
    abstract public String getName();
}
