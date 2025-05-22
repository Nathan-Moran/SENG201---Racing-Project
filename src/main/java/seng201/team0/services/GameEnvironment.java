package seng201.team0.services;

import seng201.team0.gui.MusicController;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The central class for managing the game's state and environment.
 * It holds references to all key game components like the current race, season, player's inventory,
 * shop, and provides methods for managing game flow, player data, and interactions between models.
 */
public class GameEnvironment {
    /**
     * The currently active {@link Race}.
     */
    private Race currentRace;
    /**
     * The current {@link Season} being played.
     */
    private Season currentSeason;
    /**
     * The {@link Difficulty} level selected by the player.
     */
    private Difficulty selectedDifficulty;
    /**
     * The player's current in-game currency balance.
     */
    private int balance;
    /**
     * The player's chosen name.
     */
    private String name;
    /**
     * The total length (number of races) of the season.
     */
    private int seasonLength;
    /**
     * The {@link Shop} instance, representing the in-game store.
     */
    private final Shop shopInventory;
    /**
     * The player's {@link Garage}, managing their owned cars and tuning parts.
     */
    private final Garage playerInventory = new Garage();
    /**
     * The {@link StarterCarInventory} for initial car selection.
     */
    private final StarterCarInventory starterCarInventory;
    /**
     * The {@link ShopService} for handling shop-related logic.
     */
    private final ShopService shopService;
    /**
     * The {@link ControllerService} for handling various UI-related logic.
     */
    private final ControllerService controllerService;
    /**
     * The number of races remaining in the current season.
     */
    private int racesRemaining;
    /**
     * A list storing the player's placement in each completed race.
     */
    private final List<Integer> racePlacements = new ArrayList<>();
    /**
     * The total prize money earned by the player across all races.
     */
    private int totalPrizeMoney = 0;
    /**
     * A map tracking whether the player has won each {@link Course} for the first time.
     * This is used to unlock new content.
     */
    private final Map<Course, Boolean> hasWonCourse = new HashMap<>();
    /**
     * The {@link ItemCatalogue} containing all possible cars and tuning parts in the game.
     */
    private final ItemCatalogue itemCatalogue;

    private final MusicController musicManager;

    /**
     * Constructs a new GameEnvironment, initializing all game-related components.
     * It sets up services, inventories, and initializes the course win tracking.
     */
    public GameEnvironment() {
        this.itemCatalogue = new ItemCatalogue();
        this.shopService = new ShopService(this);
        this.controllerService = new ControllerService(this);
        this.shopInventory = new Shop(this);
        this.starterCarInventory = new StarterCarInventory(this);
        this.musicManager = new MusicController();
        setupShop();
        setBalance();
        for (Course course : Course.values()) {
            hasWonCourse.put(course, false);
        }
    }

    /**
     * Gets the {@link MusicController} instance responsible for managing background music.
     *
     * @return The {@link MusicController} currently in use.
     */
    public MusicController getMusicManager() {
        return this.musicManager;
    }

    /**
     * Gets the global {@link ItemCatalogue}.
     * @return The item catalogue.
     */
    public ItemCatalogue getItemCatalogue() {
        return itemCatalogue;
    }

    /**
     * Initializes the {@link Shop}'s inventory with available and locked items.
     */
    public void setupShop() {
        shopInventory.setShopInventory();
    }

    /**
     * Sets the current {@link Race} being played.
     * @param currentRace The race to set as current.
     */
    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    /**
     * Sets the current {@link Season} being played.
     * @param season The season to set as current.
     */
    public void setCurrentSeason(Season season) {
        this.currentSeason = season;
    }

    /**
     * Sets the selected {@link Difficulty} for the game.
     * @param difficulty The difficulty level.
     */
    public void setDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
    }

    /**
     * Sets the number of races remaining in the current season.
     * @param racesRemaining The count of remaining races.
     */
    public void setRacesRemaining(int racesRemaining) {
        this.racesRemaining = racesRemaining;
    }

    /**
     * Gets the currently active {@link Race}.
     * @return The current race.
     */
    public Race getCurrentRace() {
        return currentRace;
    }

    /**
     * Gets the {@link Course} of the currently active race.
     * @return The current course, or null if no race is active.
     */
    public Course getSelectedCourse() {
        return currentRace != null ? currentRace.getCourse() : null;
    }

    /**
     * Gets the {@link Route} of the currently active race.
     * @return The current route, or null if no race is active.
     */
    public Route getSelectedRoute() {
        return currentRace != null ? currentRace.getRoute() : null;
    }

    /**
     * Gets the current {@link Season}.
     * @return The current season.
     */
    public Season getCurrentSeason() {
        return currentSeason;
    }

    /**
     * Gets the selected {@link Difficulty}.
     * @return The difficulty level.
     */
    public Difficulty getDifficulty() {
        return selectedDifficulty;
    }

    /**
     * Gets the {@link Car} currently selected by the player in their garage.
     * @return The player's selected car.
     */
    public Car getSelectedCar() {
        return playerInventory.getSelectedCar();
    }

    /**
     * Gets the number of races remaining in the season.
     * @return The count of races left.
     */
    public int getRacesRemaining() {
        return racesRemaining;
    }

    /**
     * Sets the player's name.
     * @param name The player's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the total length of the season (number of races).
     * @param seasonLength The total number of races in the season.
     */
    public void setSeasonLength(int seasonLength) {
        this.seasonLength = seasonLength;
    }

    /**
     * Gets the total length of the season.
     * @return The season length.
     */
    public int getSeasonLength() {
        return seasonLength;
    }

    /**
     * Gets the {@link ShopService} instance.
     * @return The shop service.
     */
    public ShopService getShopService() {
        return shopService;
    }

    /**
     * Gets the {@link ControllerService} instance.
     * @return The controller service.
     */
    public ControllerService getControllerService() {
        return controllerService;
    }

    /**
     * Sets up the initial starter cars in the {@link StarterCarInventory}.
     */
    public void setupStarterCars() {
        starterCarInventory.setupStarterCarInventory();
    }

    /**
     * Gets the player's {@link Garage} (inventory).
     * @return The player's garage.
     */
    public Garage getPlayerInventory() {
        return playerInventory;
    }

    /**
     * Gets the {@link Shop} instance (shop inventory).
     * @return The shop inventory.
     */
    public Shop getShopInventory() {
        return shopInventory;
    }

    /**
     * Gets the {@link StarterCarInventory}.
     * @return The starter car inventory.
     */
    public StarterCarInventory getStarterCarInventory() {
        return starterCarInventory;
    }

    /**
     * Gets the player's current balance.
     * @return The player's balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the player's balance to a specific amount.
     * @param balance The new balance.
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Sets the player's initial balance based on the selected {@link Difficulty}.
     * If no difficulty is selected, the balance defaults to 0.
     */
    public void setBalance() {
        if (selectedDifficulty != null) {
            this.balance = selectedDifficulty.getStartBudget();
        } else {
            this.balance = 0; // Default or error state
        }
    }

    /**
     * Decrements the count of races remaining in the season.
     */
    public void decrementRacesRemaining() {
        if (racesRemaining > 0) {
            racesRemaining--;
        }
    }

    /**
     * Calculates the player's average race placement across all completed races.
     * @return The average placement, or 0 if no races have been completed.
     */
    public double getAveragePlacement() {
        if (racePlacements.isEmpty()) {
            return 0; // Avoid division by zero
        }
        int sum = 0;
        for (int placement : racePlacements) {
            sum += placement;
        }
        return (double) sum / racePlacements.size();
    }

    /**
     * Gets the number of races that have been completed in the current season.
     * @return The count of completed races.
     */
    public int getRacesCompleted() {
        return seasonLength - racesRemaining;
    }

    /**
     * Adds prize money to the player's total prize money earned.
     * @param prize The amount of prize money to add.
     */
    public void addPrizeMoney(int prize) {
        this.totalPrizeMoney += prize;
    }

    /**
     * Gets the total prize money earned by the player throughout the game.
     * @return The total prize money.
     */
    public int getTotalPrizeMoney() {
        return this.totalPrizeMoney;
    }

    /**
     * Exits the application.
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * Updates the flag indicating whether the player has won a specific {@link Course} for the first time.
     * This is typically called after a race.
     *
     * @param course The {@link Course} that the player has just raced.
     * @param placement The player's placement in that race (1 for 1st, 2 for 2nd, etc.).
     */
    public void updateHasWonCourse(Course course, int placement) {
        if (placement == 1) { // Only mark as won if the player came in 1st
            hasWonCourse.put(course, true);
        }
    }

    /**
     * Checks if the player has ever won the given {@link Course} (i.e., achieved 1st place).
     * This is used to determine if new content (cars/parts) should be unlocked.
     *
     * @param course The {@link Course} to check.
     * @return {@code true} if the player has won the course, {@code false} otherwise.
     */
    public boolean hasWonCourse(Course course) {
        return hasWonCourse.getOrDefault(course, false); // Return false if course not in map
    }

    /**
     * Adds a new race placement to the historical list of placements.
     * @param placement The player's placement in the most recently completed race.
     */
    public void addRacePlacement(int placement) {
        this.racePlacements.add(placement);
    }

}