package com.mycompany.lab5.battle;

import com.mycompany.lab5.enemy.EnemyFactory;
import com.mycompany.lab5.items.Item;
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

    private Enemy[] enemies;
    private int locationNumber;

    public EnemyManager(int locationNumber) {
        this.locationNumber = locationNumber;
        initializeAllEnemies(locationNumber);
    }

    // === Метод, который создаёт и заполняет всех врагов сразу ===
    public void initializeAllEnemies(int locationNumber) {
        setEnemiesCount(locationNumber); // Устанавливаем размер массива

        List<EnemyFactory> enemyFactories = new ArrayList<>();
        enemyFactories.add(new BarakaFactory());
        enemyFactories.add(new SubZeroFactory());
        enemyFactories.add(new LiuKangFactory());
        enemyFactories.add(new SonyaBladeFactory());
        Collections.shuffle(enemyFactories);

        int index = 0;

        if (locationNumber >= 1) {
            // 2 обычных врага + 1 Шао Кан на первой локации
            for (int i = 0; i < 2; i++) {
                enemies[index++] = enemyFactories.get(i % enemyFactories.size()).createEnemy();
            }
            enemies[index++] = new ShaoKahnFactory().createEnemy(); // индекс 2
        }

        if (locationNumber >= 2) {
            // 6 обычных врагов + 1 Шао Кан на второй локации
            for (int i = 0; i < 6 && index < enemies.length; i++) {
                enemies[index++] = enemyFactories.get(i % enemyFactories.size()).createEnemy();
            }
            if (index < enemies.length) {
                enemies[index++] = new ShaoKahnFactory().createEnemy(); // индекс 7 или меньше
            } else {
                System.out.println("Ошибка: Нет места для нового Шао Кана!");
            }
        }

        if (locationNumber >= 3) {
            // 13 обычных врагов + 1 Шао Кан на третьей локации
            for (int i = 0; i < 13 && index < enemies.length; i++) {
                enemies[index++] = enemyFactories.get(i % enemyFactories.size()).createEnemy();
            }
            if (index < enemies.length) {
                enemies[index] = new ShaoKahnFactory().createEnemy(); // индекс 14 или меньше
            } else {
                System.out.println("Ошибка: Нет места для Шао Кана на третьей локации");
            }
        }
    }

    private void setEnemiesCount(int locationNumber) {
        switch (locationNumber) {
            case 1 ->
                enemies = new Enemy[3];   // 0, 1, 2
            case 2 ->
                enemies = new Enemy[8];   // 0..8
            case 3 ->
                enemies = new Enemy[15];  // 0..15
            default ->
                throw new IllegalArgumentException("Неверное число локаций: " + locationNumber);
        }
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

}
