package slimes.tools;

import javafx.scene.image.Image;
import slimes.Level;
import slimes.slime.*;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

import java.util.List;

/**
 * this tool represents a sex change that sits in your inventory.
 * @author Rene
 */


public class SexChange extends Tool {

    private final Gender gender;

    public SexChange(int amount, Gender gender) {
        super(amount);
        this.gender = gender;
    }

    /**
     * places a sex change on to the level.
     *
     * @param level the map.
     * @param pos where the tool will be used.
     * @return whether the usage of the item was successful.
     */
    @Override
    public boolean onUse(Level level, Position pos) {
        if (!level.canToolBePlacedAt(pos)) {
            return false;
        } else {
            List<Slime> slimes = level.getSlimesAt(pos);
            Slime slime = (slimes.size() > 0) ? slimes.get(0) : null;

            if (slime != null) {
                if (slime instanceof NormalSlime) {
                    NormalSlime normalSlime = (NormalSlime) slime;
                    if (normalSlime.getGender() != gender) {
                        SFXManager.playSFX(SFX.SEX_CHANGE);
                        normalSlime.setGender(gender);
                        return true;
                    }
                }
            }

            level.addPlacedTool(new PlacedSexChange(pos, gender));
            return true;
        }
    }

    /**
     * this gets the name of the tool.
     *
     * @return the name of the tool.
     */
    @Override
    public String getName() {
        switch (gender) {
            case MALE:
                return "Male Sex Change";
            case FEMALE:
                return "Female Sex Change";
            case BONUS:
                return "Bonus Sex Change";
        }
        return null;
    }

    /**
     * this gets the image that shows the texture of the item.
     *
     * @return an image object that shows the texture of an item.
     */
    @Override
    public Image getTexture() {
        switch (gender) {
            case MALE:
                return ResourceLoader.getImage("male_sex_change.png");
            case FEMALE:
                return ResourceLoader.getImage("female_sex_change.png");
            case BONUS:
                return ResourceLoader.getImage("gold_sex_change.png");
        }
        return null;
    }
}
