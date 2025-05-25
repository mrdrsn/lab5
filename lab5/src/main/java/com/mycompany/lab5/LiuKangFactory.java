package com.mycompany.lab5;



/**
 *
 * @author Мария
 */
public class LiuKangFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy(){
        return new LiuKang();
    }
}
