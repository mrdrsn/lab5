package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;


/**
 *
 * @author Мария
 */
public class SonyaBladeFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy(){
        return new SonyaBlade();
    }
}
