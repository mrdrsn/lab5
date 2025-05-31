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
public class CharacterAction {

    private Enemy enemies[] = new Enemy[6];

    private Enemy enemy = null;

    public CharacterAction() {
        setEnemyes();
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

    public void addItems(int k1, int k2, int k3, Items[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    public void useItem(Entity human, Items[] items, String name, JDialog dialog, JDialog dialog1) {
        switch (name) {
            case "jRadioButton1":
                if (items[0].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton2":
                if (items[1].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton3":
                dialog.setVisible(true);
                dialog.setBounds(300, 200, 400, 300);
                break;
        }

        if (dialog.isVisible() == false) {
            dialog1.dispose();
        }
    }
}
