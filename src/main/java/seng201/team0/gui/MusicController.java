package seng201.team0.gui;

import java.net.URL;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;


public class MusicController {
    private MediaPlayer musicPlayer;

    public void initializeMusic(String fileName) {
    try {
        String musicFilePath = fileName;
        URL musicResource = getClass().getResource(musicFilePath);

        if (musicResource == null) {
            System.err.println("Cannot find music file: " + musicFilePath);
            return;
        }
        Media sound = new Media(musicResource.toExternalForm());
        musicPlayer = new MediaPlayer(sound);
        musicPlayer.setVolume(0.05);
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(javafx.util.Duration.ZERO));
        musicPlayer.play();
    } catch (Exception e) {
        System.err.println("Error playing music: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Stops the currently playing menu music when entering a race.
     */
    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}
