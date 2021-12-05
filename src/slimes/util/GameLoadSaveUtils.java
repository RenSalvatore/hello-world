package slimes.util;

import slimes.Game;
import slimes.slime.*;
import slimes.tools.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * this class contains helper methods used from saving and loading level data in files.
 * @author Rene
 */

public class GameLoadSaveUtils {

    /**
     * this loads slimes from a scanner.
     *
     * @param in the scanner object.
     * @return an ArrayList containing slimes.
     */
    public static ArrayList<Slime> loadSlimes(Scanner in) {
        ArrayList<Slime> output = new ArrayList<Slime>();

        String slimesData = in.nextLine();
        Scanner slimeScanner = new Scanner(slimesData);
        slimeScanner.useDelimiter(";");
        while (slimeScanner.hasNext())
        {
            String[] slimeData = slimeScanner.next().split(",");
            int x = Integer.parseInt(slimeData[0]);
            int y = Integer.parseInt(slimeData[1]);
            String type = slimeData[2];

            Direction direction = null;
            boolean isSterile = false;
            if(slimeData.length > 3) {
                direction = Direction.valueOf(slimeData[3]);
                isSterile = Boolean.parseBoolean(slimeData[4]);
            }

            switch (type) {
                case "F":
                    output.add(new FemaleSlime(x, y, direction, isSterile));
                    break;
                case "M":
                    output.add(new MaleSlime(x, y, direction, isSterile));
                    break;
                case "B":
                    output.add(new BonusSlime(x, y, direction, isSterile));
                    break;
                case "Fb":
                    output.add(new BabySlime(x, y, false, Gender.FEMALE));
                    break;
                case "Mb":
                    output.add(new BabySlime(x, y, false, Gender.MALE));
                    break;
                case "Bb":
                    output.add(new BabySlime(x, y, false, Gender.BONUS));
                    break;
            }
        }

        return output;
    }

    /**
     * this turns an ArrayList of slimes to a string.
     *
     * @param slimes the list of slimes you want to save.
     * @return a String that contats data of the slimes.
     */
    public static String saveSlimes(List<Slime> slimes) {
        String output = "";

        for(Slime slime : slimes) {
            if(output.length() > 0) {
                output += ";";
            }

            output += String.format("%s,%s,%s",
                    slime.getPosition().getX(),
                    slime.getPosition().getY(),
                    getSlimeType(slime));

            if(slime instanceof AdultSlime) {
                AdultSlime adultSlime = (AdultSlime) slime;
                output += String.format(",%s,%s",
                        slime.getDirection().toString(),
                        adultSlime.isSterile() + "");
            }
        }

        return output;
    }

    /**
     * this turns an ArrayList of slimes to a string.
     *
     * @param placedTools a List of tools.
     * @return the string with all the placed tool data.
     */
    public static String savePlacedTools(List<PlacedTool> placedTools) {
        String output = "";

        for(PlacedTool placedTool : placedTools) {
            if(output.length() > 0) {
                output += ";";
            }

            output += String.format("%s,%s,%s",
                    getPlacedToolType(placedTool),
                    placedTool.getPosition().getX() + "",
                    placedTool.getPosition().getY() + "");

        }

        return output;
    }

    /**
     * this gets data from a scanner and parses the data in to an ArrayList of placed tools.
     *
     * @param in a scanner containing the data about the placed tools.
     * @return an ArrayList of Placed Tools.
     */
    public static ArrayList<PlacedTool> loadPlacedTool(Scanner in) {
        ArrayList<PlacedTool> output = new ArrayList<PlacedTool>();

        if(!in.hasNextLine()) {
            return output;
        }
        String placedToolLine = in.nextLine();

        Scanner placedToolScanner = new Scanner(placedToolLine);
        placedToolScanner.useDelimiter(";");
        while (placedToolScanner.hasNext())
        {
            String[] placedToolData = placedToolScanner.next().split(",");

            String type = placedToolData[0];
            int x = Integer.parseInt(placedToolData[1]);
            int y = Integer.parseInt(placedToolData[2]);
            Position pos = new Position(x, y);

            switch (type) {
                case "gas":
                    output.add(new PlacedGas(pos, 1));
                    break;
                case "bomb":
                    output.add(new PlacedBomb(pos));
                    break;
                case "poison":
                    output.add(new PlacedPoison(pos));
                    break;
                case "no_entry":
                    output.add(new PlacedNoEntry(pos));
                    break;
                case "sterilisation":
                    output.add(new PlacedSterilisation(pos));
                    break;
            }
        }

        return output;
    }

    /**
     * this gets a list of tools a player has and turns it into a string.
     *
     * @param tools a List of tools.
     * @return a string that contains the data of the list of tools.
     */
    public static String saveInventory(List<Tool> tools) {
        String output = "";

        for(Tool tool : tools) {
            if(output.length() > 0) {
                output += ";";
            }

            output += String.format("%s,%s",
                    tool.getName().toLowerCase().replace(" ","_"),
                    tool.getAmount());

        }

        return output;
    }

    /**
     * this gets data from a scanner and parses it in to aList of tools.
     *
     * @param in a scanner with data of the List of tools.
     * @return a List of tools.
     */
    public static List<Tool> loadInventory(Scanner in) {
        List<Tool> output = new ArrayList<Tool>();

        String toolLine = in.nextLine();
        Scanner toolScanner = new Scanner(toolLine);
        toolScanner.useDelimiter(";");
        while (toolScanner.hasNext())
        {
            String[] toolData = toolScanner.next().split(",");

            String type = toolData[0];
            int amount = Integer.parseInt(toolData[1]);

            switch (type) {
                case "bomb":
                    output.add(new Bomb(amount));
                    break;
                case "gas":
                    output.add(new GasTool(amount));
                    break;
                case "female_sex_change":
                    output.add(new SexChange(amount, Gender.FEMALE));
                    break;
                case "male_sex_change":
                    output.add(new SexChange(amount, Gender.MALE));
                    break;
                case "no_entry":
                    output.add(new NoEntryTool(amount));
                    break;
                case "poison":
                    output.add(new PoisonTool(amount));
                    break;
                case "sterilisation":
                    output.add(new SterilisationTool(amount));
                    break;
                case "death_slime":
                    output.add(new DeathSlimeTool(amount));
                    break;
                case "bonus_sex_change":
                    output.add(new SexChange(amount, Gender.BONUS));
                    break;
            }

            for(Tool tool : Game.getInstance().getInventory()) {
                String toolName = tool.getName().toLowerCase().replace(' ','_');
                if(type.equals(toolName)) {
                    tool.give(amount);
                }
            }
        }

        return output;
    }

    /**
     * a helper method which gets a string depending if the slimes male/female/bonus and baby/adult.
     *
     * @param slime the slime you want to get the type of.
     * @return a String representing the slimes type.
     */
    private static String getSlimeType(Slime slime) {
        if(slime instanceof BabySlime) {
            BabySlime babySlime = (BabySlime) slime;
            switch (babySlime.getGender()) {
                case FEMALE:
                    return "Fb";
                case MALE:
                    return "Mb";
                case BONUS:
                    return "Bb";
            }
        }
        else if( slime instanceof FemaleSlime) {
            return "F";
        } else if (slime instanceof MaleSlime) {
            return "M";
        } else if (slime instanceof  BonusSlime) {
            return "B";
        } else if (slime instanceof DeathSlime) {
            return "D";
        }
        return null;
    }

    /**
     * this gets a string based on the tools type.
     *
     * @param placedTool a placed tool that you want to get the type of.
     * @return a String representing the placed tools type.
     */
    private static String getPlacedToolType(PlacedTool placedTool) {
        if(placedTool instanceof PlacedGas) {
            return "gas";
        } else if (placedTool instanceof PlacedBomb) {
            return "bomb";
        } else if (placedTool instanceof PlacedPoison) {
            return "poison";
        } else if (placedTool instanceof PlacedNoEntry) {
            return "no_entry";
        } else if (placedTool instanceof PlacedSterilisation) {
            return "sterilisation";
        } else if (placedTool instanceof PlacedSexChange) {
            PlacedSexChange sexChange = (PlacedSexChange) placedTool;
           switch (sexChange.getGender()) {
               case FEMALE:
                   return "female_sex_change";
               case MALE:
                   return "male_sex_change";
               case BONUS:
                   return "bonus_sex_change";
           }
        }

        return null;
    }
}
