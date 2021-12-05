package slimes.slime;

import slimes.Game;
import slimes.Level;
import slimes.util.Position;
import slimes.util.SFX;
import slimes.util.SFXManager;

import java.util.List;

/**
 * This class is a helper method that checks the slimes can breed and starts the breeding process if they can.
 * @author Rene
 */

public class SlimeBreeder {

    /**
     * this checks if slimes can breed and starts the breeding process.
     */
    public static void tick() {
        Level level = Game.getInstance().getLevel();

        for (Slime slime : level.getSlimes()) {
            if (slime instanceof WombSlime) {
                WombSlime wombSlime = (WombSlime) slime;
                AdultSlime breedingPartner = findBreedingPartner(wombSlime);
                if (wombSlime.canBreed() && breedingPartner != null) {
                    startBreedingProcess(wombSlime, breedingPartner);
                }
            }
        }
    }

    /**
     * this starts the breeding process.
     *
     * @param wombSlime the slime that will give birth.
     * @param breedingPartner the slime that the slime that will give birth breed with.
     */
    private static void startBreedingProcess(WombSlime wombSlime, AdultSlime breedingPartner) {
        Position wombSlimePos = wombSlime.getPosition();
        Position partnerPos = breedingPartner.getPosition();

        if (wombSlimePos.getDistance(partnerPos) <= 1) {
            wombSlime.breedingPartner = breedingPartner;

            //if the slimes are on top of each other the slime moves back
            if (wombSlimePos.getDistance(partnerPos) == 0) {
                Position newPos = wombSlime.getPosition();
                newPos = Position.getCordsIn(wombSlime.getDirection().getBackwards(), newPos);

                wombSlime.setPosition(newPos);
            }

            wombSlime.isBreeding = true;
            breedingPartner.isBreeding = true;
            SFXManager.playSFX(SFX.SEX_SOUND);
        }
    }

    /**
     * this checks if a slime is close enough to another slime to breed with.
     *
     * @param slime the slime to check for neighbouring slimes.
     * @return a potential slime to breed with.
     */
    private static AdultSlime findBreedingPartner(WombSlime slime) {
        AdultSlime adultSime = null;
        Position lookingAtPos = slime.getPosition();

        while((!lookingAtPos.equals(Position.getCordsIn(slime.getDirection(), slime.getPosition()))) && adultSime == null ) {
            List<Slime> slimes = Game.getInstance().getLevel().getSlimesAt(lookingAtPos);

            for (Slime partnerSlime : slimes) {
                if (partnerSlime instanceof AdultSlime && slime != partnerSlime) {
                    adultSime = (AdultSlime) partnerSlime;

                    if (!slime.canBreed() || !slime.canBreedWith(adultSime)) {
                        adultSime = null;
                    }
                }
            }

            lookingAtPos = Position.getCordsIn(slime.getDirection(), lookingAtPos);
        }

        return adultSime;
    }
}
