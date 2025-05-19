package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Purchasable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PurchasableTest {

    private Purchasable purchasable;
    private final String initialName = "Test Item";
    private final int initialPrice = 100;

    @BeforeEach
    void setUp() {
        purchasable = new ConcretePurchasable(initialName, initialPrice);
    }

    @Test
    void testGetName() {
        assertEquals(initialName, purchasable.getName());
    }

    @Test
    void testSetName() {
        String newName = "New Test Item";
        purchasable.setName(newName);
        assertEquals(newName, purchasable.getName());
    }

    @Test
    void testSetNameNull() {
        purchasable.setName(null);
        assertNull(purchasable.getName());
    }

    @Test
    void testSetNameEmpty() {
        purchasable.setName("");
        assertEquals("", purchasable.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(initialPrice, purchasable.getPrice());
    }

    @Test
    void testConstructorWithDifferentValues() {
        String name = "Another Item";
        int price = 250;
        Purchasable otherPurchasable = new ConcretePurchasable(name, price);
        assertEquals(name, otherPurchasable.getName());
        assertEquals(price, otherPurchasable.getPrice());
    }

    @Test
    void testConstructorWithZeroPrice() {
        Purchasable zeroPriceItem = new ConcretePurchasable("Free Item", 0);
        assertEquals("Free Item", zeroPriceItem.getName());
        assertEquals(0, zeroPriceItem.getPrice());
    }

    @Test
    void testConstructorWithNegativePrice() {
        Purchasable negativePriceItem = new ConcretePurchasable("Discount Item", -50);
        assertEquals("Discount Item", negativePriceItem.getName());
        assertEquals(-50, negativePriceItem.getPrice());
    }
}