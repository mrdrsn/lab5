package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;

/**
 *Класс {@code Baraka} представляет собой врага "Барака", наследующегося от класса
 * {@code Enemy} и имеющего уникальные характеристики
 * @author Мария
 */
public class Baraka extends Enemy{
    private final String name = "Барака";
    
    public Baraka(){
        super(1, 100, 12);
    }
    
    public Baraka(int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
}
