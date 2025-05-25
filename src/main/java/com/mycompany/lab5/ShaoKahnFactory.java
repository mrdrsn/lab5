package com.mycompany.lab5;

/**
 *
 * @author Мария
 */
public class ShaoKahnFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy(){
        return new ShaoKahn();
    }
}
