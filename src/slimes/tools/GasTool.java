package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

/**
 * this tool represents a gas that sits in your inventory.
 * @author Rene
 */

public class GasTool extends Tool {
    private static final int INITIAL_STRENGTH = 6;

    public GasTool(int amount) {
        super(amount);
    }

    /**
     * places a gas on to the level.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether the usage of the item was successful.
     */
    @Override
    public boolean onUse(Level level, Position pos) {
        if (!level.canToolBePlacedAt(pos)) {
            return false;
        } else {
            level.addPlacedTool(new PlacedGas(pos, INITIAL_STRENGTH));
        }
        return true;
    }

    /**
     * this gets the name of the tool.
     *
     * @return the name of the tool.
     */
    @Override
    public String getName() {
        return "Gas";
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("gas_icon.png");
    }
}
