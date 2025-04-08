public abstract class Purchasable {
    private String name;
    private String description;
    private int price;

    public Purchasable(String name, int price, String description) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
