package seng201.team0;

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

    public int getPrice() {
        return price;
    }
}
