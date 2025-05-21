package seng201.team0.models;

/**
 * An abstract base class for all items in the game that can be bought or sold.
 * It defines common properties such as a name and a price.
 * Subclasses will provide specific details for different types of purchasable items
 * like {@link Car} or {@link TuningPart}.
 */
public abstract class Purchasable {
    /**
     * The name of the purchasable item.
     */
    private String name;
    /**
     * The price of the purchasable item in in-game currency.
     */
    private final int price;

    /**
     * Constructs a new Purchasable item.
     * @param name The name of the item.
     * @param price The price of the item.
     */
    public Purchasable(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the name of the purchasable item.
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the name of the purchasable item.
     * @param name The new name for the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the purchasable item.
     * @return The price of the item.
     */
    public int getPrice() {
        return price;
    }
}