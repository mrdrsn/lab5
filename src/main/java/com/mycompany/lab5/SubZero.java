package com.mycompany.lab5;

public class SubZero extends Enemy{
    private final String name = "Саб-зиро";
    
    public SubZero(){
        super(1, 60, 16);
    }
    public SubZero(int level, int health, int damage ){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return this.name;
    }
}
