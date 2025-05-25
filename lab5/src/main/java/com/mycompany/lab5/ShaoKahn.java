package com.mycompany.lab5;

/**
 *
 * @author Мария
 */
public class ShaoKahn extends Enemy {
    public ShaoKahn(){
        super(3, 100, 30, 1);
    }
    
    public ShaoKahn(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    @Override
    public String getName(){
        return "Shao Kahn";
    }
}
