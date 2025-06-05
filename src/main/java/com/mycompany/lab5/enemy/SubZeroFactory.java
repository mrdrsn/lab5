package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;



/**
 * Вспомогальный класс {@code SubZeroFactory} используется
 * для реализации паттерна "Фарбричный метод", упрощающего процесс
 * создания врагов различных типов
 * @author Мария
 */
public class SubZeroFactory extends EnemyFactory {
    @Override 
    public Enemy createEnemy(){
        return new SubZero();
    }
}
