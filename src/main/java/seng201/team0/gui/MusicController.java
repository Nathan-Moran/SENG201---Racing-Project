package seng201.team0.gui;

import java.net.URL;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;

/**
 * Manages background music playback for the application.
 * This class handles initializing, playing, and stopping music.
 */
public class MusicController {
    /**
     * The MediaPlayer instance used to play music.
     */
    private MediaPlayer musicPlayer;

    /**
     * Constructs a new MusicController.
     * This controller manages the playback of background music throughout the application.
     */
    public MusicController() {
    }

    /**
     * Initializes and starts playing background music from a specified file.
     * The music will loop indefinitely and play at a low volume.
     * If the music file cannot be found or an error occurs during playback,
     * an error message will be printed to the console.
     *
     * @param fileName The path to the music file, relative to the classpath (e.g., "/audio/background_music.mp3").
     */
    public void initializeMusic(String fileName) {
        try {
            URL musicResource = getClass().getResource(fileName);

            if (musicResource == null) {
                System.err.println("Cannot find music file: " + fileName);
                return;
            }
            Media sound = new Media(musicResource.toExternalForm());
            musicPlayer = new MediaPlayer(sound);
            musicPlayer.setVolume(0.05);
            musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
            musicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing music, if any.
     * This is typically called when transitioning from a menu (where music plays)
     * to a race scene (where music might be paused or stopped).
     */
    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}