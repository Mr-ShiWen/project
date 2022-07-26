package spi.dubbo.impl;

import spi.dubbo.services.Robot;

public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("I am Bumblebee");
    }
}
