package com.mycompany.lab5.enemy;

import com.mycompany.lab5.model.Enemy;

/**
 * *Вспомогальный класс {@code ShaoKahnFactory} используется
 * для реализации паттерна "Фарбричный метод", упрощающего процесс
 * создания врагов различных типов
 * @author Мария
 */
public class ShaoKahnFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy(){
        return new ShaoKahn();
    }
}
