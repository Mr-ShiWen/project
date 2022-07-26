package spi.impl;

import spi.services.Pet;

public class Cat implements Pet {
    @Override
    public String getName() {
        return "cat";
    }
}
