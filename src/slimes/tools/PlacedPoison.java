package slimes.tools;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.slime.Slime;
import slimes.util.Position;
import slimes.util.ResourceLoader;

/**
 * this class represents a placed poison on the level.
 * @author Rene
 */

public class PlacedPoison extends PlacedTool {

    public PlacedPoison(Position position) {
        super(position);
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("poison_puddle.png");
    }

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    @Override
    public void tick() {
        Level level = Game.getInstance().getLevel();

        for (Slime slime : level.getSlimes()) {
            Position slimePos = slime.getPosition();

            if (slimePos.equals(position)) {
                level.killSlimes(slime, true);
                destroy();
            }
        }
    }

    /**
     * this gets if slimes can go through this tool while it is placed.
     *
     * @return whether slimes can go through this placed tool.
     */
    @Override
    public boolean isSolid() {
        return false;
    }
}
