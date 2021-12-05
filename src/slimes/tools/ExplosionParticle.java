package slimes.tools;

import javafx.scene.image.Image;
import slimes.util.Position;
import slimes.util.ResourceLoader;

/**
 *  This class renders the explosion animation.
 *  @author Rene
 */

public class ExplosionParticle extends PlacedTool {

    private TextureType textureType;

    public ExplosionParticle(Position position, TextureType textureType) {
        super(position);
        this.textureType = textureType;
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    @Override
    public Image getTexture() {
        switch (textureType) {
            case HOZONTAL:
                return ResourceLoader.getImage("bomb_explosion_horizontal.png");
            case VERICAL:
                return ResourceLoader.getImage("bomb_explosion_vertical.png");
            case CENTER:
                return ResourceLoader.getImage("bomb_explosion_center.png");
        }

        return null;
    }

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    @Override
    public void tick() {
            destroy();
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

    /**
     * this gets the texture type of this placed item.
     */
    public enum TextureType {
        CENTER,
        VERICAL,
        HOZONTAL;
    }
}
