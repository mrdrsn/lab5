package com.mycompany.lab5.battle;

import com.mycompany.lab5.enemy.EnemyFactory;
import com.mycompany.lab5.items.Items;
import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.enemy.BarakaFactory;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.LiuKangFactory;
import com.mycompany.lab5.enemy.SonyaBladeFactory;
import com.mycompany.lab5.enemy.SubZeroFactory;
import com.mycompany.lab5.enemy.ShaoKahnFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JDialog;

/**
 *
 * @author Мария
 */
public class EnemyManager {

    private Enemy enemies[];
    private Enemy enemy = null;
    //эти поля можно убрать
    private int locationNumber; //всего локаций
    private int currentlocationNumber; //враги на текущую локацию

    public EnemyManager(int locationNumber) {
        setEnemiesCount(locationNumber);
        setEnemiesForLocation(1);
    }

    public void setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
    }
    private void setEnemiesCount(int locationNumber){
        switch(locationNumber){
            case 1 -> enemies = new Enemy[3];
            case 2 -> enemies = new Enemy[8];
            case 3 -> enemies = new Enemy[15];
        }
    }

    private void setEnemiesForLocation(int currentLocationNumber) {

        List<EnemyFactory> enemyFactories = new ArrayList<>();
        enemyFactories.add(new BarakaFactory());
        enemyFactories.add(new SubZeroFactory());
        enemyFactories.add(new LiuKangFactory());
        enemyFactories.add(new SonyaBladeFactory());
        Collections.shuffle(enemyFactories);
        switch (currentLocationNumber) {
            case 1 -> {
                enemies[0] = enemyFactories.get(0).createEnemy();
                enemies[1] = enemyFactories.get(1).createEnemy();
                enemies[2] = new ShaoKahnFactory().createEnemy();
            }
            case 2 -> {
                enemies[3] = enemyFactories.get(2).createEnemy();
                enemies[4] = enemyFactories.get(3).createEnemy();
                enemies[5] = enemyFactories.get(4).createEnemy();
                enemies[6] = enemyFactories.get(0).createEnemy();
                enemies[7] = new ShaoKahnFactory().createEnemy();
            }
            case 3 ->{
                enemies[8] = enemyFactories.get(1).createEnemy();
                enemies[9] = enemyFactories.get(2).createEnemy();
                enemies[10] = enemyFactories.get(3).createEnemy();
                enemies[11] = enemyFactories.get(4).createEnemy();
                enemies[12] = enemyFactories.get(0).createEnemy();
                enemies[13] = enemyFactories.get(1).createEnemy();
                enemies[14] = new ShaoKahnFactory().createEnemy();
            }
        }
    }

    //надо чтобы рандомно заполнялся массив
    private void setDefaultEnemies() {
        enemies[0] = new BarakaFactory().createEnemy();
        enemies[1] = new SubZeroFactory().createEnemy();
        enemies[2] = new LiuKangFactory().createEnemy();
        enemies[3] = new SonyaBladeFactory().createEnemy();
        enemies[4] = new ShaoKahnFactory().createEnemy();
        //другой shao kahn
        enemies[5] = new ShaoKahnFactory().createEnemy();
    }

    private void setEnemyes() {
        // Список всех возможных фабрик (кроме ShaoKahn)
        List<EnemyFactory> enemyFactories = new ArrayList<>();
        enemyFactories.add(new BarakaFactory());
        enemyFactories.add(new SubZeroFactory());
        enemyFactories.add(new LiuKangFactory());
        enemyFactories.add(new SonyaBladeFactory());

        // Перемешиваем список, чтобы получить случайный порядок
        Collections.shuffle(enemyFactories);

        // Заполняем первые 4 позиции случайными врагами из списка
        for (int i = 0; i < 4; i++) {
            enemies[i] = enemyFactories.get(i).createEnemy();
        }

        // Пятый и шестой враг — всегда Shao Kahn
        enemies[4] = new ShaoKahnFactory().createEnemy();
        enemies[5] = new ShaoKahnFactory().createEnemy();
    }

    public Enemy[] getEnemies() {
        return this.enemies;
    }

    public Enemy chooseEnemy() {
        setDefaultEnemies();
        int i = (int) (Math.random() * 4);
        switch (i) {
            case 0 -> {
                enemy = enemies[0];
            }
            case 1 -> {
                enemy = enemies[1];
            }
            case 2 -> {
                enemy = enemies[2];
            }
            case 3 -> {
                enemy = enemies[3];
            }
        }
        return enemy;
    }

    public Enemy ChooseBoss(int level) {
        switch (level) {
            case 2 ->
                enemy = enemies[4];
            case 4 -> //ShaoKahn посильнее
                enemy = enemies[5];
        }
        return enemy;
    }

}
