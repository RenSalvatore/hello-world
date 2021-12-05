package slimes.tools;

import javafx.scene.image.Image;
import slimes.Game;
import slimes.Level;
import slimes.slime.Gender;
import slimes.slime.NormalSlime;
import slimes.slime.Slime;
import slimes.util.Position;
import slimes.util.ResourceLoader;
import slimes.util.SFX;
import slimes.util.SFXManager;

/**
 * this class represents a placed sex change on the level.
 * @author Rene
 */

public class PlacedSexChange extends PlacedTool {

    private final Gender gender;

    public PlacedSexChange(Position position, Gender gender) {
        super(position);
        this.gender = gender;
    }

    /**
     * this gets the image that shows the texture of the placed tool.
     *
     * @return an image object that shows the texture of a placed tool.
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

    /**
     * a method that will define the placed tools behaviour when its on the level.
     */
    @Override
    public void tick() {
        Level level = Game.getInstance().getLevel();

        for(Slime slime : level.getSlimes()) {
            Position slimePos = slime.getPosition();

            if(slimePos.equals(position)) {
                if (slime instanceof NormalSlime) {
                    NormalSlime normalSlime = (NormalSlime) slime;
                    if(normalSlime.getGender() != gender) {
                        SFXManager.playSFX(SFX.SEX_CHANGE);
                        normalSlime.setGender(gender);
                        destroy();
                    }
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
        return false;
    }

    public Gender getGender() {
        return gender;
    }
}

