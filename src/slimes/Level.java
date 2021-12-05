package slimes;

import slimes.slime.*;
import slimes.tools.PlacedTool;
import slimes.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
    This class handles the movement of the slimes as well as the tiles.
    @author Rene
 */

public class Level {

    private Tile[][] tiles;
    private List<Slime> slimes;
    private List<PlacedTool> placedTools;
    private int populationThreshold;
    private int time;
    private int parTime;
    private List<Tuple<String, Integer>> itemGiveQueue;
    private int tick;
    private int levelHigheracy;

    /**
     * if a placedTool gets added to a list during a tick it could cause an exception so we will use.
     *  this to add a placed tool next tick.
     */
    private List<PlacedTool> placedToolsQueue;
    private List<Slime> slimeQueue;


    private Level() {
        placedTools = new ArrayList<>();
        placedToolsQueue = new ArrayList<>();
        slimeQueue = new ArrayList<>();
        tick = 0;
    }

    /**
     * gets level data from a file and turns it in to a level object.
     *
     * @param levelName name of the file containing the level data.
     * @return the level object.
     */
    public static Level loadLevel(String levelName) {
        Level level = new Level();
        File file = new File("assets/levels/" + levelName + ".txt");
        Scanner in = null;
        try
        {
            in = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found while loading: assets/levels/" + levelName + ".txt");
            return null;
        }

        int height = in.nextInt();
        int width = in.nextInt();
        in.nextLine();
        in.nextLine();
        level.tiles = loadTiles(in, height, width);
        in.nextLine();
        level.slimes = GameLoadSaveUtils.loadSlimes(in);
        in.nextLine();
        level.itemGiveQueue = loadItemGiveQueue(in);
        in.nextLine();
        level.populationThreshold = in.nextInt();
        level.parTime = in.nextInt();
        if(in.hasNextInt()) {
            level.levelHigheracy = in.nextInt();
        } else {
            level.levelHigheracy = 1;
        }

        level.time = level.parTime;

        level.checkErnouosSlimes();
        return level;
    }

    /**
     * loads the tiles that makes up a level.
     *
     * @param in Scanner object.
     * @param height height of the level.
     * @param width width of the level.
     * @return a 2D array full of tiles.
     */
    private static Tile[][] loadTiles(Scanner in, int height, int width) {
        Tile[][]  output = new Tile[height][width];
        for(int y = 0; y < height; y++) {
            String line = in.nextLine();
            for(int x = 0; x < width; x++) {
                switch (line.charAt(x))
                {
                    case 'p':
                        output[y][x] = Tile.PATH;
                        break;
                    case 'd':
                        output[y][x] = Tile.DIRT;
                        break;
                    case 't':
                        output[y][x] = Tile.TUNNEL;
                        break;
                }
            }
        }

        return output;
    }

    /**
     * This loads the order of which the items are given to the player.
     *
     * @param in the scanner containing the data of the item give queue.
     * @return a list of the data the needs to be given.
     */
    private static List<Tuple<String, Integer>> loadItemGiveQueue(Scanner in) {
        ArrayList<Tuple<String, Integer>> output = new ArrayList<>();

        String itemsData = in.nextLine();
        Scanner itemScanner = new Scanner(itemsData);
        itemScanner.useDelimiter(";");
        while (itemScanner.hasNext())
        {
            String itemData = itemScanner.next();

            String tool =  itemData.split(",")[0];
            int giveAt = Integer.parseInt(itemData.split(",")[1]);

            output.add(new Tuple<>(tool, giveAt));
        }

        return output;
    }

    /**
     * this updates all the slimes and placed tools on the level.
     */
    public void tick() {
        for (Slime slime : slimes) {
            if(slime instanceof BabySlime || tick % 2 == 0) {
                slime.tick();
            }
        }
        SlimeBreeder.tick();


        if(tick % 2 == 0) {
            placedTools.forEach(PlacedTool::tick);
            placedTools.removeIf(PlacedTool::shouldBeRemoved);
        }

        placedTools.addAll(placedToolsQueue);
        placedToolsQueue.clear();

        slimes.addAll(slimeQueue);
        slimeQueue.clear();

        giveToolCheck();

        if(tick % 2 == 0) {
            time--;
        }
        tick++;
    }

    /**
     * this checks wether if a level has been defined.
     *
     * @param levelName the level you want to it if it exists.
     * @return a boolean. true means the level exists.
     */
    public static boolean doesLevelExist(String levelName) {
        File file = new File("assets/levels/" + levelName + ".txt");
        return file.exists();
    }

    /**
     *  this checks if we need to give the player a tool this second.
     */
    private void giveToolCheck() {
        final int gameTime = parTime - time;

        for(Tuple<String,Integer> tool : itemGiveQueue) {
            if(tool.get(1) instanceof Integer) {
                int giveAt = (Integer) tool.get(1);

                if(gameTime == giveAt) {
                    String toolName = tool.get(0).toString().toLowerCase().replace(' ','_');
                    Game.getInstance().giveItem(toolName, 1);
                }
            }
        }
    }

    /**
     * this gets a tile on the level from the specified position.
     *
     * @param position the positon you want to get the tile at.
     * @return a tile object.
     */
    public Tile getTileAt(Position position) {
        int x = position.getX();
        int y = position.getY();

        if(isInbounds(x, y)) {
            return tiles[y][x];
        } else {
            return null;
        }
    }

    /**
     * gets the height of the level.
     *
     * @return the height of the level.
     */
    public int getHeight() {
        return tiles.length;
    }

    /**
     * gets the width of the level.
     *
     * @return the width of the level.
     */
    public int getWidth() {
        if(getHeight() > 0) {
            return tiles[0].length;
        } else {
            return 0;
        }
    }

    /**
     * @return all the slimes on the level.
     */
    public List<Slime> getSlimes()
    {
        return slimes;
    }

    /**
     * this kills a single slime from this level.
     *
     * @param slimeToKill a slime you want to kill.
     * @param awardPoints if this is false the player will not earn any points from the slimes removed.
     */
    public void killSlimes(Slime slimeToKill, boolean awardPoints) {
        ArrayList<Slime> slimesToKill = new ArrayList<Slime>();
        slimesToKill.add(slimeToKill);
        killSlimes(slimesToKill, awardPoints);
    }

    /**
     * removes the specified slimes from the level.
     *
     * @param slimesToKill a list of slimes you want to kill.
     * @param awardPoints if this is false they player will not earn any points from the slimes removed.
     */
    public void killSlimes(List<Slime> slimesToKill, boolean awardPoints) {
        if(slimesToKill.size() == 0) {
            return;
        }

        ArrayList<Slime> newSlimes = new ArrayList<>();

        for(Slime slime : slimes) {
            if(!slimesToKill.contains(slime)) {
                newSlimes.add(slime);
            } else {
                if(awardPoints) {
                    Game.getInstance().changeScore(slime.pointsOnKill());
                    SFXManager.playSFX(SFX.POINT);
                }
            }
        }

        slimes = newSlimes;
    }

    /**
     * this gets whether the given x and y cord is in bounds of the level.
     *
     * @param x the x cord.
     * @param y the y cord.
     * @return a boolean determining if the x and y cord is within bounds of the level.
     */
    public boolean isInbounds(int x, int y)
    {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    /**
     * this adds a placed tool on the map.
     *
     * @param placedTool a placed tool you want to place.
     */
    public void addPlacedTool(PlacedTool placedTool) {
        placedToolsQueue.add(placedTool);
    }

    /**
     * this gets all the placed tools on the map.
     *
     * @return a list of all the placed tools.
     */
    public List<PlacedTool> getPlacedTools() {
        return placedTools;
    }

    /**
     * this adds a slime on to the level.
     *
     * @param slime a slime you want to add to the level.
     */
    public void addSlime(Slime slime) {
        slimeQueue.add(slime);
    }

    /**
     * gets all the slimes at the specified position.
     *
     * @param pos the position you want to get all the slimes at.
     * @return a list of slimes.
     */
    public List<Slime> getSlimesAt(Position pos) {
        List<Slime> output = new ArrayList<>();

        for(Slime slime : slimes) {
            if(slime.getPosition().equals(pos)) {
                output.add(slime);
            }
        }

        return output;
    }

    /**
     * This gets if a spot on the map is valid for a slime or a tool ot something to be on.
     *
     * @param position a position.
     * @return whether if its valid or not.
     */
    public boolean isValidPosition(Position position) {
        PlacedTool placedTool = null;
        if(placedTools.size() > 0) {
            for (PlacedTool currentPlacedTool : placedTools) {
                if (currentPlacedTool.getPosition().equals(position)) {
                    placedTool = currentPlacedTool;
                }
            }
        }

        Tile tile = getTileAt(position);
        if(tile != null) {
            if(placedTool != null) {
                return !(placedTool.isSolid() || tile.isSolid());
            } else {
                return !tile.isSolid();
            }
        } else {
            return false;
        }
    }

    /**
     * this gets whether if the given positions if valid for a tool to be placed on.
     *
     * @param position a position.
     * @return a boolean value whether the tool can be placed at that position.
     */
    public boolean canToolBePlacedAt(Position position) {
        Tile tile = getTileAt(position);
        if(tile != null) {
            return isValidPosition(position) && !tile.isTunnel();
        } else {
            return false;
        }
    }

    /**
     * gets the population threshold.
     *
     * @return the population threshold.
     */
    public int getPopulationThreshold() {
        return populationThreshold;
    }

    /**
     * this gets the count of each gender on the level.
     *
     * @return a map object where the key is the gender and the value is the count.
     */
    public Map<Gender, Integer> getGenderPopulations() {
        Map<Gender,Integer> output = new HashMap<>();
        output.put(Gender.FEMALE, 0);
        output.put(Gender.MALE, 0);
        output.put(Gender.BONUS, 0);

        for(Slime slime : slimes) {
            if(slime instanceof NormalSlime) {
                NormalSlime normalSlime = (NormalSlime) slime;
                Gender gender = normalSlime.getGender();
                output.replace(gender,output.get(gender) + 1);
            }
        }

        return output;
    }

    /**
     * gets the population.
     *
     * @return the population.
     */
    public int getPopulation() {
        return (int) slimes.stream().filter(slime -> slime instanceof NormalSlime).count();
    }

    /**
     * gets the time left,
     *
     * @return the time left,
     */
    public int getTime() {
        return time;
    }

    /**
     * gets the time the level is expected to be completed in.
     *
     * @return the time the level is expected to be completed in.
     */
    public int getParTime() {
        return parTime;
    }

    /**
     * gets the level number of this level.
     *
     * @return the level number of this level.
     */
    public int getLevelHigheracy() {
        return levelHigheracy;
    }

    /**
     * sets this levels slimes.
     *
     * @param slimes a list of slimes.
     */
    protected void setSlimes(List<Slime> slimes) {
        this.slimes = slimes;
    }

    /**
     * sets the placed tools on the level.
     *
     * @param placedTools a list of placed tools.
     */
    protected void setPlacedTools(List<PlacedTool> placedTools) {
        this.placedTools = placedTools;
    }

    /**
     * sets the time left.
     *
     * @param time the time left you want to set it to.
     */
    protected void setTime(int time) {
        this.time = time;
    }

    /**
     * dev method to check whether the slimes have been placed in valid positions.
     * if not the program says so in the console and crashes.
     */
    private void checkErnouosSlimes() {
        boolean error = false;
        for(Slime slime : slimes) {
            Position pos = slime.getPosition();
            Tile tile = getTileAt(pos);
            if(tile == null) {
                System.err.println(String.format("A slime is spawning on a null tile at %s", slime.getPosition()));
                error = true;
            }
            else if(tile.isSolid()) {
                System.err.println(String.format("A slime at %s is spawning in a wall", slime.getPosition()));
                error = true;
            }
        }

        if(error) {
            System.exit(0);
        }
    }
}
