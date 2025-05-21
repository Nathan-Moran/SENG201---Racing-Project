package seng201.team0.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides static descriptions for various cars and tuning parts in the game.
 * This service acts as a centralized lookup for explanatory text that can be
 * displayed in the UI (e.g., in shop or garage screens).
 */
public class DescriptionService {

    /**
     * A static map storing descriptions for different car models.
     * Keys are car names (String), values are their descriptions (String).
     */
    private static final Map<String, String> carDescriptions = new HashMap<>();
    /**
     * A static map storing descriptions for different tuning parts.
     * Keys are tuning part names (String), values are their descriptions (String).
     */
    private static final Map<String, String> tuningPartDescriptions = new HashMap<>();

    // Static initializer block to populate the description maps when the class is loaded
    static {
        carDescriptions.put("Honda Civic", "A well-rounded entry point, the Civic R offers a good blend of peppy acceleration and commendable dependability, with decent fuel usage. Its cornering ability is adequate for learning the ropes. A solid choice for early events like Desert Drift or for a driver who values a reliable all-rounder.");
        carDescriptions.put("Mazda MPS", "The Mazda MPS shines with its notably agile handling among the starters, making it nimble on more technical early tracks like Desert Drift or Mountain Curves. Its straight-line speed is modest, but it's a dependable and fuel-efficient companion for those who prefer precision driving.");
        carDescriptions.put("Nissan Z", "If durability is your priority, the Nissan Z boasts excellent reliability for a starter car, inspiring confidence. Its speed and handling are competent, making it a trusty steed for challenging early routes where finishing is paramount, like a careful run on Mountain Steep. Good fuel economy adds to its pragmatism.");
        carDescriptions.put("Toyota Supra", "A legend for a reason. The Supra delivers a potent combination of high top speeds and impressively sharp handling, backed by solid reliability. Its versatility makes it a strong contender for a wide array of tracks, from the fast Desert Long to the demanding Country Twist. It also manages its fuel well for its class.");
        carDescriptions.put("Mustang", "Unleash American muscle! The Mustang boasts incredible straight-line acceleration, making it a beast on open roads like Country Straight or the tight sprints of City Alleys. Its handling requires a confident driver to tame, and while reliable, its thirst for fuel means watching your pit strategy.");
        carDescriptions.put("Ferrari", "An exotic masterpiece delivering blistering top speed and almost telepathic handling. The Ferrari 458 is a top-tier performer, capable of dominating almost any route if driven skillfully. High reliability ensures it can take the heat, but its racing pedigree means it consumes fuel quickly. A dream machine for ambitious racers.");

        carDescriptions.put("Dune Drifter", "Unlocked by conquering the Desert, the Dune Drifter is a true handling specialist. Its modest top speed is more than offset by its incredible agility on loose surfaces, making it dance where others struggle. Good reliability and fuel economy make it a smart choice for technical desert stages.");
        carDescriptions.put("Ridge Racer", "Victory on Mountain Curves unlocks the Ridge Racer, a car that lives for corners thanks to its superb road-holding. Its outright speed is secondary to its incredible ability to dissect technical sections with precision. Good reliability and impressive fuel economy make it an efficient choice for twisty routes.");
        carDescriptions.put("Vineyard Viper", "Unlocked by winning Country Twist, this car excels in agility, feeling light and responsive through corners. It's perfect for flowing through sequences of bends, with adequate speed, good reliability, and solid fuel economy.");
        carDescriptions.put("CommuterKing", "Master the chaos of the City to unlock the Commuter King. Its standout feature is its rock-solid dependability, ensuring it won't let you down. While not built for outright speed, its decent handling helps navigate jams, and its excellent fuel efficiency makes it a surprisingly effective urban tool.");

        tuningPartDescriptions.put("Ethanol", "Boosts speed by improving engine combustion with high-octane fuel.");
        tuningPartDescriptions.put("SuperCharger", "Significantly increases speed by forcing more air into the engine.");
        tuningPartDescriptions.put("TurboKit", "Provides a massive speed boost by using exhaust gases to spin a turbine and force more air into the engine.");
        tuningPartDescriptions.put("StreetWheels", "Improves handling with better grip for everyday driving conditions.");
        tuningPartDescriptions.put("SportsWheels", "Significantly enhances handling with tires designed for spirited driving and tighter cornering.");
        tuningPartDescriptions.put("RacingWheels", "Maximizes handling performance with professional-grade slick tires for ultimate track grip.");
    }

    /**
     * Retrieves the descriptive text for a given car model.
     * @param carName The name of the car.
     * @return The description of the car, or a default message if no description is found.
     */
    public static String getCarDescription(String carName) {
        return carDescriptions.getOrDefault(carName, "Car has not description");
    }

    /**
     * Retrieves the descriptive text for a given tuning part.
     * @param partName The name of the tuning part.
     * @return The description of the tuning part, or a default message if no description is found.
     */
    public static String getTuningPartDescription(String partName) {
        return tuningPartDescriptions.getOrDefault(partName, "Part has not description");
    }
}