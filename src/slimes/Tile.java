package slimes;

/**
 * This represents the possible tiles in a level and defines their properties.
 *
 * @author Will
 */

public enum Tile
{
    DIRT (true, false),
    TUNNEL (false,true),
    PATH (false, false);

    private final boolean isSolid;
    private final boolean isTunnel;

    Tile (boolean isSolid, boolean isTunnel) {
        this.isSolid = isSolid;
        this.isTunnel = isTunnel;
    }

    /**
     * if a tile is solid then that means slimes and placed tools cant be on it.
     *
     * @return whether is the tile is solid.
     */
    public boolean isSolid() {
        return isSolid;
    }

    /**
     * if a tile is a tunnel then that means rats and placed tools cant be displayed on it.
     *
     * @return whether if a Tile is a tunnel.
     */
    public boolean isTunnel() {
        return isTunnel;
    }
}
