package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.slime.Slime;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

import java.util.List;

/**
 * this tool represents a poison that sits in your inventory.
 * @author Rene
 */

public class PoisonTool extends Tool {
    public PoisonTool(int amount) {
        super(amount);
    }

    /**
     * places a poison on to the level.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether the usage of the item was successful.
     */
    @Override
    public boolean onUse(Level level, Position pos) {
        if(!level.canToolBePlacedAt(pos)) {
            return false;
        } else {
            List<Slime> slimes = level.getSlimesAt(pos);
            if (slimes.size() == 0) {
                level.addPlacedTool(new PlacedPoison(pos));
                SFXManager.playSFX(SFX.POTION_BREAK);
            } else {
                level.killSlimes(slimes, true);
            }
            return true;
        }
    }

    /**
     * this gets the name of the tool.
     *
     * @return the name of the tool.
     */
    @Override
    public String getName() {
        return "Poison";
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("poison.png");
    }
}
