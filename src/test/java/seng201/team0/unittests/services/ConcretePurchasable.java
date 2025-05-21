package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Purchasable;

import static org.junit.jupiter.api.Assertions.*;

// Concrete class for testing abstract Purchasable
class ConcretePurchasable extends Purchasable {
    public ConcretePurchasable(String name, int price) {
        super(name, price); //
    }
}

class PurchasableTest {

    @Test
    void constructorSetsNameAndPrice() {
        String name = "Test Item";
        int price = 100;
        Purchasable item = new ConcretePurchasable(name, price); //

        assertEquals(name, item.getName()); //
        assertEquals(price, item.getPrice()); //
    }

    @Test
    void getNameReturnsCorrectName() {
        Purchasable item = new ConcretePurchasable("Another Item", 50); //
        assertEquals("Another Item", item.getName()); //
    }

    @Test
    void setNameUpdatesName() {
        Purchasable item = new ConcretePurchasable("Old Name", 75); //
        assertEquals("Old Name", item.getName()); //
        item.setName("New Name"); //
        assertEquals("New Name", item.getName()); //
    }

    @Test
    void getPriceReturnsCorrectPrice() {
        Purchasable item = new ConcretePurchasable("Pricy Item", 1000); //
        assertEquals(1000, item.getPrice()); //
    }

    @Test
    void constructorWithZeroPrice() {
        Purchasable item = new ConcretePurchasable("Free Item", 0); //
        assertEquals("Free Item", item.getName()); //
        assertEquals(0, item.getPrice()); //
    }

    @Test
    void constructorWithEmptyName() {
        Purchasable item = new ConcretePurchasable("", 200); //
        assertEquals("", item.getName()); //
        assertEquals(200, item.getPrice()); //
    }
}