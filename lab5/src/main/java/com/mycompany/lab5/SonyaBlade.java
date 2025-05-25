package com.mycompany.lab5;

/**
 *
 * @author Мария
 */
public class SonyaBlade extends Enemy{
    public SonyaBlade(){
        super(1, 80, 16, 1);
    }
    public SonyaBlade (int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    @Override
    public String getName(){
        return "Sonya Blade";
    }
}
