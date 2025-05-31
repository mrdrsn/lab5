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

    @Override
    public void applyDebuff(Entity opponent) {
        int getMoveCount = opponent.getLevel() + 1;
        if (opponent.getAttack() == 1) {
            int chance = random.nextInt(100);
            if (chance < 75) {
                System.out.println("Ослабление применено. По вам увеличен урон на 25% и ваш урон уменьшен на 50% на " 
                        + getMoveCount + " ходов");
            }
        } else {
            opponent.increaseDamageByPercentage(15);
            System.out.println("Ослабление сорвалось. По врагу увеличен урон на " + getMoveCount + " ходов.");
        }
    }
}
