package seng201.team0.models;

public abstract class Purchasable {
    private String name;
    private int price;

    public Purchasable(String name, int price) {
        this.name = name;
        this.price = price;
    }

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

    public int getPrice() {
        return price;
    }
}
