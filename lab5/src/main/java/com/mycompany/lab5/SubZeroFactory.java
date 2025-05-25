package com.mycompany.lab5;



/**
 *
 * @author Мария
 */
public class SubZeroFactory extends EnemyFactory {
    @Override 
    public Enemy createEnemy(){
        return new SubZero();
    }
}
