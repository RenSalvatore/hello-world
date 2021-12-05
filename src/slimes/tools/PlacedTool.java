package slimes.tools;

import javafx.scene.image.Image;
import slimes.util.Position;

/**
 * this class defined behavior of a placed tool.
 * @author Rene
 */

public abstract class PlacedTool {

    /**
        The position of the tool.
     */
    protected final Position position;

    /**
     *  if this is true this will get removed.
     */
    private boolean remove;

    public PlacedTool(Position position) {
        this.position = position;
    }

    /**
     * this gets if the placed tool should be removed.
     *
     * @return whether the placed tool should be removed.
     */
    public boolean shouldBeRemoved() {
        return remove;
    }

    /**
     * this gets the positon of the placed tool.
     *
     * @return the position of the placed tool.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    abstract public Image getTexture();

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    abstract public void tick();

    /**
     * this gets if slimes can go through this tool while it is placed.
     *
     * @return whether slimes can go through this placed tool.
     */
    abstract public boolean isSolid();

    /**
     * this tells the Level class to remove this tool.
     */
    protected void destroy() {
        remove = true;
    }
}
