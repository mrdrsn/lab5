package com.mycompany.lab5;

/**
 *
 * @author nsoko
 */
 public class GameEngine {
    private final BattleEngine battleEngine = new BattleEngine();
    private final LevelManager levelManager = new LevelManager();

    // CharacterAction используется как фабрика врагов
    private final CharacterAction characterAction = new CharacterAction();

    public void startGame(Player human) {
        // Сначала инициализируем список всех возможных врагов
//        characterAction.setEnemyes();

        while (!isGameOver(human)) {
            Enemy enemy = chooseNextEnemy(human);
            BattleLog log = battleEngine.fight(human, enemy);

            if (human.isAlive()) {
                levelManager.addExperience(human, characterAction.getEnemyes());
                checkForRevive(human); // если нужно, воскрешаем через предметы
            }

            if (human.getWin() == 11) {
                break;
            }
        }
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