/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import javax.swing.ImageIcon;

/**
 *
 * @author Мария
 */
public class Entity {
    
    private int level;
    private int health;
    private int maxhealth;
    private int damage;
    private int attack;
    private ImageIcon icon;
    
    public Entity(int level, int health, int damage){
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.maxhealth = health;
    }
    public boolean isAlive() {
        return this.health > 0;
    }
   
    public void setLevel(){
        this.level++;
    }
    public void setHealth(int h){
        this.health += h;
    }
    public void setNewHealth(int h){
        this.health = h;
    }
    public void setDamage(int d){
        this.damage += d;
    }
    public void setAttack(int a){
        this.attack = a;
    }
    public void setMaxHealth(int h){
        this.maxhealth += h;
    }
    
    public int getLevel(){
        return this.level;
    }
    public int getHealth(){
        return this.health;
    }
    public int getDamage(){
        return this.damage;
    }
    public int getAttack(){
        return this.attack;
    }
    public int getMaxHealth(){
        return this.maxhealth;
    }
    
    public String getName(){
        return null;
    }
    
}
