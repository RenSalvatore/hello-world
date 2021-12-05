package slimes.tools;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.slime.Slime;
import slimes.Tile;
import slimes.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * this class reprsents a placed gas on the level.
 * @author Rene
 */

public class PlacedGas extends PlacedTool {

    /**
     *  this value will get multiplied by the strength of the gas.
     *  the final number is the number of seconds the gas will stay on the screen.
     */
    private static final int DECAY_TIMER_MUTI = 2;

    /**
     *  The % chance the gas will kill a rat every tick.
     */
    private static final int GAS_KILL_CHANCE = 33;

    private int strength;
    private int decayTimer;
    private boolean hasSpread;

    public PlacedGas(Position position, int strength) {
        super(position);
        this.strength = strength;
        this.decayTimer = strength * DECAY_TIMER_MUTI;
        hasSpread = false;
        SFXManager.playSFX(SFX.GAS_SPREADING);
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    @Override
    public Image getTexture() {
        return ResourceLoader.getImage("gas.png");
    }

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    @Override
    public void tick() {
        Level level = Game.getInstance().getLevel();

        //makes the gas spread
        if(!hasSpread && strength > 0) {
            for(Direction direction : Direction.values()) {
                Position pos = Position.getCordsIn(direction, position);
                Tile tile = level.getTileAt(pos);
                if(tile != null) {
                    if (!tile.isSolid()) {
                        level.addPlacedTool(new PlacedGas(pos, strength - 1));
                    }
                }
            }

            hasSpread = true;
        }

        //kills the slimes if they are in the gas.
        List<Slime> slimesToKill = new ArrayList<>();
        for(Slime slime : level.getSlimesAt(position)) {
                if(Game.getInstance().getRandom().nextInt(100) < GAS_KILL_CHANCE) {
                    slimesToKill.add(slime);
                }
        }
        level.killSlimes(slimesToKill, true);

        //delete the gas if its been on the map for too long
        decayTimer--;
        if(decayTimer <= 0) {
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
