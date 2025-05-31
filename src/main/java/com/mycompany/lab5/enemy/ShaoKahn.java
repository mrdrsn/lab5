package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.model.Boss;

/**
 *
 * @author Мария
 */
public class ShaoKahn extends Enemy implements Boss {
    private final String name = "Шао Кан";
    
    public ShaoKahn(){
        super(3, 100, 30);
    }
    
    public ShaoKahn(int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return this.name;
    }
}
