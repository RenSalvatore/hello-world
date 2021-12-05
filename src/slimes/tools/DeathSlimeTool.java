package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.slime.DeathSlime;
import slimes.util.Position;
import slimes.util.ResourceLoader;

/**
 * this tool represents a death slime that sits in your inventory.
 * @author Rene
 */

public class DeathSlimeTool extends Tool
{
    public DeathSlimeTool(int amount)
    {
        this.amount = amount;
    }

    /**
     * places a death slime on to the level.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether the usage of the item was successful.
     */
    public boolean onUse(Level level, Position pos) {
        if (!level.canToolBePlacedAt(pos)) {
            return false;
        } else {
            level.addSlime(new DeathSlime(pos.getX(), pos.getY()));
            return true;
        }
    }

    /**
     * this gets the name of the tool.
     *
     * @return the name of the tool.
     */
    public String getName() {
        return "Death Slime";
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("killer_slime_down.png");
    }
}
