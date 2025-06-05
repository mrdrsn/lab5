package com.mycompany.lab5.battle;

import com.mycompany.lab5.items.ItemManager;
import com.mycompany.lab5.model.Debuffer;
import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.ShaoKahn;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс (@code GameEngine) представляет собой ядро игры, 
 * в котором обрабатываются все процессы, взаимодействующие
 * с пользовательским интерфейсом напрямую.
 * Например, удар, повтор раунда, переход на новый уровень, окончание игры
 * 
 * @author nsoko
 */
public class GameEngine {

    private int locationNumber;
    private int currentLocationNumber = 1;
    private int enemiesLeft;
    private final BattleEngine battleEngine;
    private final EnemyManager enemyManager;
    private final LevelManager levelManager = new LevelManager();
    private final ItemManager itemManager = new ItemManager();

    private Enemy[] enemies;
    private int roundNumber = 0;
    private boolean reachedNewLevel = false;
    private boolean gameOver = false;

    public GameEngine(int locationNumber) {
        System.out.println("Выбранное число локаций " + locationNumber);
        this.locationNumber = locationNumber;
        enemyManager = new EnemyManager(locationNumber);
        enemies = enemyManager.getEnemies();

        battleEngine = new BattleEngine();
        battleEngine.setEnemy(enemies[0]);
    }

    public void hit(Player human, Enemy enemy, int action) {
        battleEngine.processAction(human, enemy, action);
    }

    public void addWin(Player player) {
        reachedNewLevel = false;
        player.setWin();
        itemManager.addRandomItem();
        if (battleEngine.getEnemy() instanceof ShaoKahn) {
            levelManager.addBossExperience(player, enemies);
            System.out.println("Номер раунда - " + roundNumber);
            System.out.println("Количество врагов - " + enemies.length);
            if ((roundNumber + 1) == enemies.length) {
                gameOver = true;
                return;
            } else {
                currentLocationNumber++;
//                enemyManager.setEnemiesForLocation(currentLocationNumber);

            }
        }
        levelManager.addExperience(player, enemies);
        if (levelManager.isNewLevel()) {
            reachedNewLevel = true;
        }
        roundNumber++;
    }

    public void repeatLastRound(Player player) {
        Enemy lastEnemy = battleEngine.getEnemy();
        battleEngine.setTurn(false);
        player.setFullHealth();
        lastEnemy.setFullHealth();
    }

    public void startNewRound(Player player) {
        reachedNewLevel = false;
        battleEngine.setTurn(false);
        System.out.println("новый раунд под номером " + roundNumber);
        System.out.println("длина массива enemies " + enemies.length);
        for (Enemy e : enemies) {
            if (e != null) {
                System.out.println(e.getName());
            } else {
                System.out.println("Враг не создан");
            }
        }
        System.out.println("Номер текущей локации " + currentLocationNumber);
        Enemy nextEnemy = enemies[roundNumber];
        battleEngine.setEnemy(nextEnemy);
        player.setFullHealth();
        nextEnemy.setFullHealth();
    }

    public void boostDamage(Player player) {
        player.updateDamage((int) (player.getDamage() * 0.25));
    }

    public void boostMaxHealth(Player player) {
        player.updateMaxHealth((int) (player.getMaxHealth() * 0.25));
    }

    public boolean isPlayerStunned() {
        return this.battleEngine.isPlayerStunned();
    }

    public boolean isEnemyStunned() {
        return this.battleEngine.isEnemyStunned();
    }

    public boolean isPlayerDebuffed() {
        return this.battleEngine.isPlayerDebuffed();
    }

    public boolean isEnemyDebuffed() {
        return this.battleEngine.isEnemyDebuffed();
    }

    public boolean isPlayerTurn() {
        return this.battleEngine.isPlayerTurn();
    }

    public int getLocationNumber() {
        return this.locationNumber;
    }

    public int getCurrentLocationNumber() {
        return this.currentLocationNumber;
    }

    public boolean isNewLevel() {
        return this.reachedNewLevel;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public BattleEngine getBattleEngine() {
        return this.battleEngine;
    }

    public Enemy getFirstEnemy() {
        return this.enemies[0];
    }

    public Enemy getNextEnemy() {
        return battleEngine.getEnemy();
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

}
