package com.mycompany.lab5;

public class SubZero extends Enemy{
    public SubZero(){
        super(1, 60, 16, 1);
    }
    public SubZero(int level, int health, int damage , int attack){
        super (level, health, damage, attack);
    }
    
    @Override
    public String getName(){
        return "Sub-Zero";
    }
}
