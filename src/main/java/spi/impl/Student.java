package spi.impl;

import spi.services.User;

public class Student implements User {
    @Override
    public String getName() {
        return "student";
    }
}
