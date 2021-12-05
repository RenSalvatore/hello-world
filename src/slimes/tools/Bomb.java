package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

/**
 * A bomb tool that sits in your inventory.
 * @author Rene
 */

public class Bomb extends Tool
{
    public Bomb(int amount)
    {
        this.amount = amount;
    }

    /**
     * places a bomb on to the level.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether the usage of the item was successful.
     */
    protected boolean onUse(Level level, Position pos) {
        if (!level.canToolBePlacedAt(pos)) {
            return false;
        } else {
            level.addPlacedTool(new PlacedBomb(pos));
            SFXManager.playSFX(SFX.COUNTDOWN);
        }
        return true;
    }

    /**
     * this gets the name of the tool.
     *
     * @return the name of the tool.
     */
    public String getName() {
        return "Bomb";
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    public Image getTexture() {
        return ResourceLoader.getImage("bomb.png");
    }
}
