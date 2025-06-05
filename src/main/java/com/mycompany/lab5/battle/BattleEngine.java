package com.mycompany.lab5.battle;

import com.mycompany.lab5.enemy.ShaoKahn;
import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;

//Здесь проигрывается весь бой с одним противником: ходы, изменение здоровья, применение предметов и т.д. 
public class BattleEngine {

    private final CombatSystem combat = new CombatSystem();
    private final EnemyBehaviorManager behaviorManager = new EnemyBehaviorManager();
    private Enemy enemy;
    private boolean isPlayerTurn = false;

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void processAction(Player human, Enemy enemy, int playerAction) {
        human.setAttack(playerAction);
        behaviorManager.registerPlayerAction(playerAction);
        behaviorManager.isPlayerTurn(isPlayerTurn);
        int enemyAction = behaviorManager.getBehavior(enemy);

        // Если босс пытается регенерировать
        if (enemyAction == -1) {
            if (playerAction == 0 || playerAction == 2) {
                ((ShaoKahn) enemy).regenerateHealth();
                System.out.println("Шао Кан успешно восстановил здоровье!");
            } else if (playerAction == 1) {
                int doubleDamage = human.getDamage() * 2;
                enemy.setHealth(-doubleDamage);
                System.out.println("Регенерация Шао Кана прервана. Он получил двойной урон!");
            }
        } else {
            enemy.setAttack(enemyAction);
            combat.setPlayerTurn(isPlayerTurn);
            if (isPlayerTurn == true) {
                combat.processMove(human, enemy); //если очередь игрока то human = attacker, enemy = defender
            } else {
                combat.processMove(enemy, human);
            }
        }
        if (enemy instanceof ShaoKahn && playerAction == 1) {
            ((ShaoKahn) enemy).cumulateDamage(human.getDamage());
        }
        isPlayerTurn = !isPlayerTurn;
    }

    public boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }

    public void setTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public boolean isPlayerStunned() {
        return combat.isPlayerStunned();
    }

    public boolean isEnemyStunned() {
        return combat.isEnemyStunned();
    }

    public boolean isPlayerDebuffed() {
        return combat.isPlayerDebuffed();
    }

    public boolean isEnemyDebuffed() {
        return combat.isEnemyDebuffed();
    }
}
