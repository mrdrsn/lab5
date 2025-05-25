package com.mycompany.lab5;


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
