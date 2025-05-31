package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;

/**
 *
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
