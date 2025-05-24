/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;



/**
 *
 * @author Мария
 */
public class LiuKangFabric extends EnemyFactory implements EnemyFabricInterface {
    @Override
    public Enemy createEnemy(){
        return new LiuKang();
    }
    
    @Override
    public Player create(int i) {
        Player enemy;
        enemy = new LiuKang(1, 70, 20, 1);
        return enemy;
    }
}
