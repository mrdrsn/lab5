/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

/**
 *
 * @author nsoko
 */
public class BattleResult {
    private final Entity attacker;
    private final Entity defender;
    private final boolean isCounterattack;
    private final boolean isStunned;
    private final int damage;
    private final boolean roundOver;

    public BattleResult(Entity attacker, Entity defender, int damage, boolean isCounterattack, boolean isStunned, boolean roundOver) {
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.isCounterattack = isCounterattack;
        this.isStunned = isStunned;
        this.roundOver = roundOver;
    }

    // Getters
    public Entity getAttacker() { return attacker; }
    public Entity getDefender() { return defender; }
    public int getDamage() { return damage; }
    public boolean isCounterattack() { return isCounterattack; }
    public boolean isStunned() { return isStunned; }
    public boolean isRoundOver() { return roundOver; }
    
    @Override
    public String toString(){
        return "Атаковал: " + this.attacker.getName() + "\n Отвечал: " + defender.getName() +
                "\n был контратакован: " + isCounterattack + "\n был оглушен: " + isStunned
                + "\n урон: " + damage + "\n раунд окончен: " + roundOver;
    }
}