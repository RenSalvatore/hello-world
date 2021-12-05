package slimes.util;

import javafx.scene.media.AudioClip;
import javax.sound.sampled.*;
import java.io.IOException;

/** Manages the sound effects and background music for the game.
 * @author Sandesh Adhikari
 * @version 0.1
 */
public class SFXManager {
    private Clip menuClip;
    private Clip levelClip;

    /**
     * Initialises the variables for the menu and level sounds.
     */
    public SFXManager(){
        try {
            menuClip = AudioSystem.getClip();
            menuClip.open(ResourceLoader.getBGMusic("BGMusic1.wav"));

            levelClip = AudioSystem.getClip();
            levelClip.open(ResourceLoader.getBGMusic("BGMusic3.wav"));

        }catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the background music for the game on repeat when the player is on a menu screen.
     */
    public void playMenuMusic(){
        levelClip.stop(); //Stops overlapping music
        menuClip.start();
        menuClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays the background music for the game on repeat when the player is in a level.
     */
    public void playLevelMusic(){
        menuClip.stop();
        levelClip.start();
        levelClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Gets an audio clip from a file.
     * @param sfx the sound effect enum get.
     * @return an AudioClip object.
     */
    public static AudioClip getSFX(SFX sfx){
        return ResourceLoader.getSFX(sfx.getPath());
    }

    /**
     * Plays a sound effect based.
     * @param sfx which sound to play.
     */
    public static void playSFX(SFX sfx){
        getSFX(sfx).play(sfx.getVolume());
    }

    /**
     * Plays a sound effect with a different volume.
     * @param sfx which sound to play.
     * @param volume changes the volume of the sound.
     */
    public static void playSFX(SFX sfx, double volume){
        getSFX(sfx).play(volume);
    }
}
