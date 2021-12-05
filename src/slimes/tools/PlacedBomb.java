package slimes.tools;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import slimes.Game;
import slimes.Level;
import slimes.Tile;
import slimes.slime.Slime;
import slimes.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * this class represents a placed bomb on the level.
 * @author Rene
 */

public class PlacedBomb extends PlacedTool {

    private static final int START_BOMB_FUSE = 4;

    /**
     *  The timer for when the bomb explodes.
     */
    private int fuse;

    public PlacedBomb(Position position) {
        super(position);
        fuse = START_BOMB_FUSE;
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
     */
    @Override
    public Image getTexture() {
        switch (fuse)
        {
            case 3:
                return ResourceLoader.getImage("bomb_three.png");
            case 2:
                return ResourceLoader.getImage("bomb_two.png");
            case 1:
                return ResourceLoader.getImage("bomb_one.png");
            default:
                return ResourceLoader.getImage("bomb_four.png");
        }
    }

    /**
     * a method that will define the placed tools behaviour when its on the leve.l
     */
    @Override
    public void tick() {
        if (fuse > 0) {
            fuse--;
        }

        if (fuse == 0) {
            explode();
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

    /**
     * this calculates the tiles affected and kills all the slimes on that tile.
     */
    private void explode() {
        Level level = Game.getInstance().getLevel();

        //getting the tiles in all directions
        List<Position> tilesUp =  getTilesUntilWall(Direction.UP);
        List<Position> tilesDown =  getTilesUntilWall(Direction.DOWN);
        List<Position> tilesLeft =  getTilesUntilWall(Direction.LEFT);
        List<Position> tilesRight =  getTilesUntilWall(Direction.RIGHT);

        // concating all the lists together
        List<Position> allTiles = tilesUp;
        allTiles.addAll(tilesDown);
        allTiles.addAll(tilesLeft);
        allTiles.addAll(tilesRight);

        List<Slime> slimesToKill = new ArrayList<>();
        for (Slime slime : level.getSlimes())
        {
            Position slimePos = slime.getPosition();
            if (allTiles.contains(slimePos)) {
                slimesToKill.add(slime);
            }
        }

        level.killSlimes(slimesToKill, true);

        showBombExplosion(new HashSet<>(allTiles));
    }

    /**
     * this draws the explosion on to the screen.
     *
     * @param explosionTiles the tiles have have been affected by the explosion.
     */
    private void showBombExplosion(Set<Position> explosionTiles) {
        Level level = Game.getInstance().getLevel();

        for(Position tilePos : new HashSet<>(explosionTiles)) {
            int xDiff = position.getX() - tilePos.getX();
            int yDiff = position.getY() - tilePos.getY();

            if (position.equals(tilePos)) {
                level.addPlacedTool(new ExplosionParticle(tilePos, ExplosionParticle.TextureType.CENTER));
            } else if(xDiff != 0) {
                level.addPlacedTool(new ExplosionParticle(tilePos, ExplosionParticle.TextureType.HOZONTAL));
            } else if(yDiff != 0) {
                level.addPlacedTool(new ExplosionParticle(tilePos, ExplosionParticle.TextureType.VERICAL));
            }
        }
    }

    /**
     * This gets a list of tiles starting at a given direction and goes in that direction until it hits a wall.
     *
     * @param direction the direction to check.
     * @return a list of positions it found before hitting a wall.
     */
    private List<Position> getTilesUntilWall(Direction direction)
    {
        Level level = Game.getInstance().getLevel();
        List<Position> output = new ArrayList<Position>();

        boolean tileIsSolid;
        Position lookingAtPos = position;
        do {
            output.add(lookingAtPos);
            lookingAtPos = Position.getCordsIn(direction, lookingAtPos);

            Tile tile = level.getTileAt(lookingAtPos);
            if (tile != null) {
                tileIsSolid = tile.isSolid();
            } else {
                tileIsSolid = true;
            }
        }
        while (!tileIsSolid);

        return output;
    }
}
