package spi.impl;

import spi.services.Pet;

public class Dog implements Pet {
    @Override
    public String getName() {
        return "dog";
    }
}
