package slimes.slime;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.util.Position;
import slimes.util.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents a death slime. death slimes can kill other slimes.
 * @author Rene
 */

public class DeathSlime extends Slime {

    private static final int STATIONARY_TIME = 2;
    private static final int MAX_HEALTH = 5;

    private int health;

    public DeathSlime(int x, int y) {
        super(x, y);
        health = MAX_HEALTH;
    }

    /**
     *  This gets the texture of the slime.
     *  @return an Image Object with the image of the slime.
     */
    @Override
    public Image getTexture() {
        switch (direction) {
            case UP:
                return ResourceLoader.getImage("killer_slime_up.png");
            case DOWN:
                return ResourceLoader.getImage("killer_slime_down.png");
            case LEFT:
                return ResourceLoader.getImage("killer_slime_right.png");
            case RIGHT:
                return ResourceLoader.getImage("killer_slime_left.png");
        }

        return null;
    }

    /**
     * this handles the slimes logic.
     */
    @Override
    public void tick()
    {
        if(age >= STATIONARY_TIME) {
            Level level = Game.getInstance().getLevel();
            Position nextPos = Position.getCordsIn(direction, position);

            List<Slime> slimesToKill = level.getSlimesAt(position);
            slimesToKill.addAll(level.getSlimesAt(nextPos));
            List<Slime> slimesKilled = new ArrayList<>();

            while (health > 0 && slimesToKill.size() > 0) {
                Slime slime = slimesToKill.get(0);
                if(slime instanceof NormalSlime && !slimesKilled.contains(slime)) {
                    level.killSlimes(slime, true);
                    slimesKilled.add(slime);
                    health--;
                }
                slimesToKill.remove(slime);
            }

            if (health <= 0) {
                level.killSlimes(this, false);
            }

            move();
        }

        age++;
    }

    /**
     * this defines the number of points given to the player when this slime is killed.
     * @return the number of points given to the player.
     */
    @Override
    public int pointsOnKill() {
        return 0;
    }
}
