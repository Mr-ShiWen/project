package spi.impl;

import spi.services.User;

public class Teacher implements User {
    @Override
    public String getName() {
        return "teacher";
    }
}
