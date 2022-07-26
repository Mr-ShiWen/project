package reflection.impl;

import reflection.interf.Pet;

public class Cat implements Pet {
    public static void sleep(int hours){
        System.out.println("I will sleep for " + hours + " hours.[Cat]");
    }
    @Override
    public void sayHello(String name, int age) {
        System.out.println("Hello World, I am a Cat, My name is "+name+", I am "+age+" years old!");
    }
}
