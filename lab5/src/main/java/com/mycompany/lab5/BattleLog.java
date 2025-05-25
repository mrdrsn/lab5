/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author nsoko
 */
public class BattleLog {
    private final ArrayList<BattleResult> results = new ArrayList<>();
    private final Player player;
    private final Enemy enemy;

    public BattleLog(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void addResult(BattleResult result) {
        results.add(result);
    }

    public boolean isOver() {
        return !player.isAlive() || !enemy.isAlive();
    }

    public ArrayList<BattleResult> getResults() {
        return results;
    }
//    @Override
//    public String toString(){
//        rut
//    }
}
