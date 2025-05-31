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
    private final BattleEngine battleEngine;
    private final LevelManager levelManager = new LevelManager();
    private final ItemManager itemManager = new ItemManager();
    private final CharacterAction characterAction = new CharacterAction();

    private Enemy[] enemies = characterAction.getEnemies();
    private int roundNumber = 0;
    private boolean reachedNewLevel = false;
    private boolean isGameOver = false;

    public GameEngine(BattleEngine be) {
        this.battleEngine = be;
        this.battleEngine.setEnemy(enemies[0]);
    }

    public Enemy getEnemy(int number) {
        return this.enemies[number];
    }

    public void setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
        manageLocation();
    }

    public boolean isNewLevel() {
        return this.reachedNewLevel;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public final void manageLocation() {
        switch (locationNumber) {
            case (1) -> {
                enemies = new Enemy[3];
                enemies[0] = characterAction.getEnemies()[0];
                enemies[1] = characterAction.getEnemies()[1];
                enemies[2] = characterAction.getEnemies()[4]; //босс
            }
            case (2) -> {
                enemies = new Enemy[6];
                enemies[0] = characterAction.getEnemies()[0];
                enemies[1] = characterAction.getEnemies()[1];
                enemies[2] = characterAction.getEnemies()[4]; //босс
                enemies[3] = characterAction.getEnemies()[2];
                enemies[4] = characterAction.getEnemies()[3];
                enemies[5] = characterAction.getEnemies()[5]; //босс
            }
            case (3) -> {
                enemies = new Enemy[3];
                enemies[0] = characterAction.getEnemies()[0];
                enemies[1] = characterAction.getEnemies()[1];
                enemies[2] = characterAction.getEnemies()[4];
            }

        }
    }

    public void hit(Player human, Enemy enemy, int action) {
        if (action == 2 && human instanceof Debuffer) {
            ((Debuffer) human).applyDebuff(enemy);
            handleDebuffEffect(human, enemy);
        } else {
            battleEngine.processAction(human, enemy, action);
        }
    }

    private void handleDebuffEffect(Player player, Enemy enemy) {
        boolean isDefending = (enemy.getAttack() == 0); // предположим, есть такой метод

        if (isDefending) {
            // С вероятностью 75% накладываем дебаф
            if (Math.random() < 0.75) {
                int turns = player.getLevel(); // длительность = уровень игрока
                enemy.applyWeakness(turns);
                System.out.println("Враг ослаблен на " + turns + " ходов.");
            } else {
                System.out.println("Противник успешно заблокировал ослабление.");
            }
        } else {
            // Если противник атакует — ослабление срывается, игрок получает бонус к урону
            player.increaseDamageByPercentage(15);
            System.out.println("Вы получили бонус к урону +15%");
        }
    }

    public void manageExp(Player player) {

    }

    public void startNewRound(Player player, boolean hasPlayerWon) {
//        manageLocation();
        reachedNewLevel = false;
        Enemy lastEnemy = battleEngine.getEnemy();
        battleEngine.setTurn(false);
        if (hasPlayerWon) {
            itemManager.addRandomItem();
            roundNumber++;
            if (roundNumber == enemies.length) {
                isGameOver = true;
            } else {
                player.setWin();
                Enemy nextEnemy = enemies[roundNumber];
                battleEngine.setEnemy(nextEnemy);
                //тут еще проверка levelmanager
                player.setFullHealth();
                nextEnemy.setFullHealth();
            }
        } else {
            player.setFullHealth();
            lastEnemy.setFullHealth();
        }
    }

    public void addWin(Player player) {
        reachedNewLevel = false;
        player.setWin();
        if (battleEngine.getEnemy() instanceof ShaoKahn) {
            levelManager.addBossExperience(player, enemies);
        }
        levelManager.addExperience(player, enemies);
        if (levelManager.isNewLevel()) {
            reachedNewLevel = true;
        }
        System.out.println("Текущее число побед игрока: " + player.getWin());
    }

    public boolean getGameOver() {
        return this.isGameOver;
    }

}
