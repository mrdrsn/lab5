package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;



/**
 * Вспомогальный класс {@code LiuKangFactory} используется
 * для реализации паттерна "Фарбричный метод", упрощающего процесс
 * создания врагов различных типов
 * @author Мария
 */
public class LiuKangFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy(){
        return new LiuKang();
    }
}
