package com.mycompany.lab5;
/**
 *
 * @author Мария
 */
public class BarakaFactory  extends EnemyFactory{

    @Override
    public Enemy createEnemy() {
        return new Baraka();
    }
}
