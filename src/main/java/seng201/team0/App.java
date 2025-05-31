package seng201.team0;

import seng201.team0.gui.MainWindow;

/**
 * Default entry point class for the application.
 * This class contains the main method that launches the JavaFX application.
 * @author seng201 teaching team
 */
public class App {


    /**
     * Entry point which runs the JavaFX application.
     * Due to how JavaFX works, {@code MainWindow.launchWrapper()} must be called from here.
     * Attempting to run {@code MainWindow} directly will cause an error due to JavaFX's
     * application lifecycle requirements.
     * @param args program arguments from command line.
     */
    public static void main(String[] args) {
        MainWindow.launchWrapper(args);
    }
}