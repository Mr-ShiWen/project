package reflection.impl;

import reflection.interf.Pet;

public class Dog implements Pet {
    @Override
    public void sayHello(String name, int age) {
        System.out.println("Hello World, I am a Dog, My name is "+name+", I am "+age+" years old!");
    }
}
