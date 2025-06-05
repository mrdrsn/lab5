package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.model.Boss;

/**
 * Класс {@code ShaoKahn} представляет собой врага "Шао Кан", наследующегося от класса
 * {@code Enemy} и имеющего уникальные характеристики. Этот враг также является боссом,
 * поэтому он имеет уникальную механику регенерации, прописанную в интерфейсе {@code Boss})
 * @author Мария
 */
public class ShaoKahn extends Enemy implements Boss {

    private final String name = "Шао Кан";
    private int totalDamageRecieved = 0;

    public ShaoKahn() {
        super(3, 100, 30);
    }

    public ShaoKahn(int level, int health, int damage) {
        super(level, health, damage);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void regenerateHealth() {
        setHealth((int) (totalDamageRecieved*0.5));
        if(getHealth() > getMaxHealth()){
            setNewHealth(getMaxHealth());
        }
    }

    @Override
    public void cumulateDamage(int damage) {
        totalDamageRecieved += damage;
    }
}
