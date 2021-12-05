package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

/**
 * this tool represents a sterilisation that sits in your inventory.
 * @author Rene
 */


public class SterilisationTool extends Tool {

    public SterilisationTool(int amount) {
        super(amount);
    }

    /**
     * places a sterilisation on to the level.
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
            SFXManager.playSFX(SFX.STERLISATION);
            level.addPlacedTool(new PlacedSterilisation(pos));
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
        return "Sterilisation";
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("sterilisation.png");
    }
}
