package slimes.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import slimes.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * a class that helps load Game assets.
 *
 * @author Rene
 * @author Sandesh
 */

public class ResourceLoader
{
    /**
     * gets an image from the asset's folder.
     *
     * @param path the path to the image.
     * @return an image object.
     */
    public static Image getImage(String path) {
        FileInputStream stream = getFileFromUrl("assets/img/" + path);
        assert stream != null;
        return new Image(stream);
    }

    /**
     * Loads a media file (sfx) e.g. .mp3 file.
     *
     * @param path the path to the media.
     * @return a media object.
     */
    public static AudioClip getSFX(String path){
        return new AudioClip(Objects.requireNonNull(Main.class.getResource("/sfx/" + path)).toString());
    }

    /**
     * Loads an audio file (.wav) for the background music.
     *
     * @param path the path to the media.
     * @return AudioInputStream object.
     */
    public static AudioInputStream getBGMusic(String path){
        File soundFile = new File("assets/sfx/bg/"+path);
        try {
            return AudioSystem.getAudioInputStream(soundFile);

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * finds a fxml file based on what you typed in.
     *
     * @param path the path to the fxml file.
     * @return returns FXMLLoader Object
     */
    public static FXMLLoader getFXML(String path) {
        return new FXMLLoader(Main.class.getResource("/fxml/" + path));
    }

    public static String getCSS(String path) {
        return Objects.requireNonNull(Main.class.getResource("/fxml/" + path)).toExternalForm();
    }

    /**
     * this gets an asset from the asset's folder.
     *
     * @param path the path to the desired asset.
     * @return a FileInputStream with the asset.
     */
    private static FileInputStream getFileFromUrl(String path) {
        try {
            return new FileInputStream(path);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
