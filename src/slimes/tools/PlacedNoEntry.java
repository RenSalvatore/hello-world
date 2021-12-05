package slimes.tools;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.slime.Slime;
import slimes.util.Position;
import slimes.util.ResourceLoader;

/**
 * this class represents a placed no entry on the level.
 * @author Rene
 */

public class PlacedNoEntry extends PlacedTool {

    public static final int MAX_HEALTH = 5;

    private int health;

    public PlacedNoEntry(Position position) {
        super(position);
        health = MAX_HEALTH;
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    @Override
    public Image getTexture() {
        switch (health) {
            case 5:
                return ResourceLoader.getImage("no_entry_five.png");
            case 4:
                return ResourceLoader.getImage("no_entry_four.png");
            case 3:
                return ResourceLoader.getImage("no_entry_three.png");
            case 2:
                return ResourceLoader.getImage("no_entry_two.png");
            case 1:
                return ResourceLoader.getImage("no_entry_one.png");
            default:
                return ResourceLoader.getImage("no_entry_five.png");
        }
    }

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    @Override
    public void tick() {
        Level level = Game.getInstance().getLevel();

        for(Slime slime : level.getSlimes()) {
            Position nextSlimePos = Position.getCordsIn(slime.getDirection(), slime.getPosition());

            if(nextSlimePos.equals(position)) {
                health--;
                if(health <= 0) {
                    destroy();
                }
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
        return true;
    }
}
