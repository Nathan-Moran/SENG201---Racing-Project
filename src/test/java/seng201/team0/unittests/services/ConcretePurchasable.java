package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Purchasable;

import static org.junit.jupiter.api.Assertions.*;

class ConcretePurchasable extends Purchasable {
    public ConcretePurchasable(String name, int price) {
        super(name, price);
    }
}

