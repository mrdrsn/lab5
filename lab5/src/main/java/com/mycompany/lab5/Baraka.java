package com.mycompany.lab5;

/**
 *
 * @author Мария
 */
public class Baraka extends Enemy{
    public Baraka(){
        super(1, 100, 12, 1);
    }
    
    public Baraka(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    @Override
    public String getName(){
        return "Baraka";
    }
    
}
