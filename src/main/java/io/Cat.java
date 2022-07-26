package io;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cat implements Serializable {

    private static final long serialVersionUID = 1313079980933474275L;

    private int age;
    private String name;

    public Cat(int age, String name) {
        this.age = age;
        this.name = name;
    }


}
