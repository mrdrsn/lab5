/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;



/**
 *
 * @author Мария
 */
public class SubZeroFabric extends EnemyFactory implements EnemyFabricInterface {
    @Override 
    public Enemy createEnemy(){
        return new SubZero();
    }
    @Override
    public Player create(int i) {
        Player enemy;
        enemy = new SubZero(1, 60, 16, 1);
        return enemy;
    }

}
