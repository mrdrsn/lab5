package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.model.Debuffer;
import com.mycompany.lab5.model.Entity;
import java.util.Random;

public class SubZero extends Enemy implements Debuffer {

    private Random random = new Random();
    private final String name = "Саб-зиро";

    public SubZero() {
        super(1, 60, 16);
    }

    public SubZero(int level, int health, int damage) {
        super(level, health, damage);
    }

    @Override
    public String getName() {
        return this.name;
    }

    
}
