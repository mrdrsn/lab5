package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;

/**
 * Класс {@code SonyaBlade} представляет собой врага "Соня Блэйд", наследующегося от класса
 * {@code Enemy} и имеющего уникальные характеристики
 * @author Мария
 */
public class SonyaBlade extends Enemy{
    private final String name = "Соня Блэйд";
    
    public SonyaBlade(){
        super(1, 80, 16);
    }
    public SonyaBlade (int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return this.name;
    }
}
