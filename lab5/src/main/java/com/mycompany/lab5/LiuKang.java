package com.mycompany.lab5;

/**
 *
 * @author Мария
 */
public class LiuKang extends Enemy{
    public LiuKang(){
        super(1, 70, 20, 1);
    }
    
    public LiuKang(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    @Override
    public String getName(){
        return "Liu Kang";
    }
}
