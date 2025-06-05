package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;

/**
 *Класс {@code LiuKang} представляет собой врага "Ли Кан", наследующегося от класса
 * {@code Enemy} и имеющего уникальные характеристики
 * @author Мария
 */
public class LiuKang extends Enemy{
    private final String name = "Лю Кан";
    public LiuKang(){
        super(1, 70, 20);
    }
    
    public LiuKang(int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return this.name;
    }
}
