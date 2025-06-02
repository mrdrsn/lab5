package com.mycompany.lab5.battle;

import com.mycompany.lab5.items.ItemManager;
import com.mycompany.lab5.model.Debuffer;
import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.ShaoKahn;

/**
 *
 * @author nsoko
 */
public class GameEngine {

    private int locationNumber;
    private int currentLocationNumber = 1;
    private final BattleEngine battleEngine;
    private final EnemyManager enemyManager;
    private final LevelManager levelManager = new LevelManager();
    private final ItemManager itemManager = new ItemManager();

    private Enemy[] enemies;
    private int roundNumber = 0;
    private boolean reachedNewLevel = false;
    private boolean gameOver = false;

    public GameEngine(int locationNumber) {
        enemyManager = new EnemyManager(locationNumber);
        battleEngine = new BattleEngine();
        enemies = enemyManager.getEnemies();
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
            if (roundNumber == enemies.length) {
                gameOver = true;
            } else {
                currentLocationNumber++;
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
        Enemy nextEnemy = enemies[roundNumber];
        battleEngine.setEnemy(nextEnemy);
        player.setFullHealth();
        nextEnemy.setFullHealth();
    }

    public boolean isPlayerStunned(){
        return this.battleEngine.isPlayerStunned();
    }
    public boolean isEnemyStunned(){
        return this.battleEngine.isEnemyStunned();
    }
    public boolean isPlayerTurn(){
        return this.battleEngine.getPlayerTurn();
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
