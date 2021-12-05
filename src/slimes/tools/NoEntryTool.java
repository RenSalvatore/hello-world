package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.slime.Slime;
import slimes.util.Position;
import slimes.util.ResourceLoader;

/**
 * this tool represents a no entry that sits in your inventory.
 * @author Rene
 */


public class NoEntryTool extends Tool {
    public NoEntryTool(int amount) {
        super(amount);
    }

    /**
     * places a no entry on to the level.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether the usage of the item was successful.
     */
    @Override
    public boolean onUse(Level level, Position pos) {
        int minDistanceFromSlime = Integer.MAX_VALUE;
        for (Slime slime : level.getSlimes()) {
            int distance = pos.getDistance(slime.getPosition());
            minDistanceFromSlime = Math.min(minDistanceFromSlime, distance);
        }

        if (!level.isValidPosition(pos) || minDistanceFromSlime <= 1) {
            return false;
        } else {
            level.addPlacedTool(new PlacedNoEntry(pos));
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
        return "No Entry";
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("no_entry.png");
    }
}
