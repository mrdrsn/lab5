/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.model;

/**
 * Класс {@code Entity} является общим классом для сущностей "игрок"
 * и "враг". Представляет собой набор базовых характеристик и методов, присущих
 * всем типам сущностей.
 * @author Мария
 */
public class Entity {

    private int level;
    private int health;
    private int maxHealth;
    private int damage;
    private int attack;
    private boolean stunned = false;
    private boolean debuffed = false;

    public Entity(int level, int health, int damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
    }

    public void setLevel() {
        this.level++;
    }

    public void setAttack(int a) {
        this.attack = a;
    }
    
    public void setNewHealth(int h) {
        this.health = h;
    }
    
    public void setFullHealth() {
        this.health = this.maxHealth;
    }
    
    public void setHealth(int h) {
        this.health += h;
    }

    public void updateDamage(int d) {
        this.damage += d;
    }


    public void updateMaxHealth(int h) {
        this.maxHealth += h;
    }

    public int getLevel() {
        return this.level;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }
    
    //потом убрать
    public void setDamage(int damage){
        this.damage = damage;
    }
//    public void increaseHealthByPercentage(int percent) {
//        int increase = this.maxHealth * percent / 100;
//        this.maxHealth += increase;
//        this.health = this.maxHealth; // Восстанавливаем полное здоровье
//    }

    public void healByPercentage(int percent) {
        int healAmount = this.maxHealth * percent / 100;
        this.health = Math.min(this.health + healAmount, this.maxHealth);
    }

    public void healToRevive() {
        setHealth((int) (this.maxHealth*0.05)); // 5%
    }

    public String getName() {
        return null;
    }
    public boolean isStunned(){
        return this.stunned;
    }
    public void setStunned(boolean stunned){
        this.stunned = stunned;
    }
    
    public boolean isDebuffed(){
        return this.debuffed;
    }
    public void setDebuffed(boolean debuffed){
        this.debuffed = debuffed;
    }

}
