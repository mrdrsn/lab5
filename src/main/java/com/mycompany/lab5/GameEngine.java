package com.mycompany.lab5;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nsoko
 */
public class GameEngine {

    private final BattleEngine battleEngine;
    private final LevelManager levelManager = new LevelManager();

    // CharacterAction используется как фабрика врагов
    private final CharacterAction characterAction = new CharacterAction();

    public GameEngine(BattleEngine be) {
        this.battleEngine = be;
    }

    public void startGame(Player human) {
        // Сначала инициализируем список всех возможных врагов
//        characterAction.setEnemyes();

        while (!isGameOver(human)) {
            Enemy enemy = chooseNextEnemy(human);
//            BattleLog log = battleEngine.fight(human, enemy);

            if (human.isAlive()) {
                levelManager.addExperience(human, characterAction.getEnemyes());
                checkForRevive(human); // если нужно, воскрешаем через предметы
            }

            if (human.getWin() == 11) {
                break;
            }
        }
    }
    
    public void hit(Player player, Enemy enemy, int attackCode){
        battleEngine.processAction(player, enemy, attackCode);
    }

    public void startNewRound(Player player, boolean hasPlayerWon) {
        Enemy lastEnemy = battleEngine.getEnemy();
        battleEngine.setTurn(false);
        if (hasPlayerWon) {
            player.setWin();
            battleEngine.setEnemy();
            while (battleEngine.getEnemy().getName() == lastEnemy.getName()) {
                battleEngine.setEnemy();
            }
            //тут еще проверка levelmanager
            player.setFullHealth();
            lastEnemy.setFullHealth();
        } else {
            player.setFullHealth();
            lastEnemy.setFullHealth();
        }
    }

    public void addWin(Player player) {
        player.setWin();
        System.out.println("Текущее число побед игрока: " + player.getWin());
    }

    private Enemy chooseNextEnemy(Player human) {
        int winCount = human.getWin();

        // Проверяем, нужен ли босс
        if (winCount == 6 || winCount == 11) {
            return characterAction.ChooseBoss(human.getLevel());
        } else {
            return characterAction.ChooseEnemy();
        }
    }

    private boolean isGameOver(Player human) {
        return !human.isAlive() || human.getWin() == 11;
    }

    private void checkForRevive(Player human) {
        // Здесь можно добавить логику использования креста возрождения
        // например:
        // itemManager.tryAutoRevive(human);
    }
}
