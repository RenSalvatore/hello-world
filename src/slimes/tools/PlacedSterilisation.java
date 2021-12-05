package slimes.tools;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.slime.BabySlime;
import slimes.slime.NormalSlime;
import slimes.slime.Slime;
import slimes.util.Position;
import slimes.util.ResourceLoader;

import java.util.List;

/**
 * this class represents a placed sterilisation on the level.
 * @author Rene
 */

public class PlacedSterilisation extends PlacedTool {

    private static final int DURATION = 4;
    public static final int RADIUS = 1;

    private int timer;

    public PlacedSterilisation(Position position) {
        super(position);
        timer = DURATION;
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    @Override
    public Image getTexture() {
        switch(timer) {
            case 4:
                return ResourceLoader.getImage("radiation_four.png");
            case 3:
                return ResourceLoader.getImage("radiation_three.png");
            case 2:
                return ResourceLoader.getImage("radiation_two.png");
            case 1:
                return ResourceLoader.getImage("radiation_one.png");
            default:
                return ResourceLoader.getImage("radiation_four.png");
        }
    }

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    @Override
    public void tick() {
        Level level = Game.getInstance().getLevel();
        List<Slime> slimes = level.getSlimes();

        for(Slime slime : slimes) {
            if(slime instanceof NormalSlime) {
                if(slime.getPosition().getDistance(position) <= RADIUS) {
                    NormalSlime normalSlime = (NormalSlime) slime;
                    normalSlime.becomeSterile();
                }
            }
        }

        timer--;
        if(timer <= 0) {
            destroy();
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
