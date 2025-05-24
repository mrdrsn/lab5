/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;


/**
 *
 * @author Мария
 */
public class SonyaBladeFabric extends EnemyFactory implements EnemyFabricInterface {
    @Override
    public Enemy createEnemy(){
        return new SonyaBlade();
    }
    @Override
    public Player create(int i) {
        Player enemy;
        enemy = new SonyaBlade(1, 80, 16, 1);
        return enemy;
    }

}
