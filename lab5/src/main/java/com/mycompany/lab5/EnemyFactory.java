/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import com.mycompany.lab5.BarakaFactory;
import com.mycompany.lab5.EnemyFabricInterface;
import com.mycompany.lab5.LiuKangFabric;
import com.mycompany.lab5.Player;
import com.mycompany.lab5.ShaoKahnFabric;
import com.mycompany.lab5.SonyaBladeFabric;
import com.mycompany.lab5.SubZeroFabric;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Мария
 */
public abstract class EnemyFactory {
    public abstract Enemy createEnemy();
    //ее странный метод
//    public Player create(int i, int j) {
//        EnemyFabricInterface fabric = null;
//
//        switch (i) {
//            case 0:
//                fabric = new BarakaFabric();
//                break;
//            case 1:
//                fabric = new SubZeroFabric();
//                break;
//            case 2:
//                fabric = new LiuKangFabric();
//                break;
//            case 3:
//                fabric = new SonyaBladeFabric();
//                break;
//            case 4:
//                fabric = new ShaoKahnFabric();
//                break;
//        }
//        Player enemy = fabric.create(j);
//        return enemy;
//    }
}
