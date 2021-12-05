package slimes.util;

/**
 * A class that defines the paths to certain sound effects.
 *
 * @author Sandesh
 */

public enum SFX {
    BOMB_EXPLOSION ("bomb1.mp3",0.4),
    POINT ("point.mp3", 0.4),
    POTION_BREAK ("potion break 3.mp3", 0.4),
    BUTTON_CLICK ("Click sound.mp3", 1),
    COUNTDOWN("Bomb.mp3", 0.4),
    BONUS_SLIME("bonus_slime.mp3", 0.4),
    GAS_SPREADING("Gas spreading.mp3", 0.4),
    SEX_SOUND("Slime breeding.mp3", 0.4),
    WIN("Win_sound.mp3",0.4),
    STERLISATION("Sterelization.mp3", 0.4),
    SEX_CHANGE("Sex_change.mp3", 0.4),
    LOSE("Lose_sound.mp3",0.4);

    private String path;
    private double volume;

     SFX (String path, double volume) {
        this.path = path;
        this.volume = volume;
    }

    /**
     * gets the path of the audio.
     *
     * @return the path of the audio.
     */
    public String getPath() {
        return path;
    }

    /**
     * gets the default volume of the sound.
     *
     * @return the default volume of the sound.
     */
    public double getVolume() {
        return volume;
    }
}
