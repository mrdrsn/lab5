
package com.mycompany.lab5;


//Здесь проигрывается весь бой с одним противником: ходы, изменение здоровья, применение предметов и т.д. 
public class BattleEngine {
    
    private final CombatSystem combat = new CombatSystem();
    private final EnemyBehaviorManager behaviorManager = new EnemyBehaviorManager();
//    private final ItemManager itemManager = new ItemManager();
    private int patternIndex = 0;
    private boolean isPlayerTurn = true;
    
    public BattleLog fight(Player human, Enemy enemy) {
        BattleLog log = new BattleLog(human, enemy);

        int[] enemyPattern = behaviorManager.getBehaviorFor(enemy);
        int step = 0;

        while (!log.isOver()) {
            System.out.println(step);
            int enemyAttack = enemyPattern[step % enemyPattern.length];
            enemy.setAttack(enemyAttack);

            // Ход игрока
            BattleResult result = combat.processMove(human, enemy, true);
            log.addResult(result);
            if (enemy.getHealth() <= 0 || human.getHealth() <= 0) {
                break;
            }

            // Ход противника
            result = combat.processMove(enemy, human, false);
            log.addResult(result);

            if (enemy.getHealth() <= 0 || human.getHealth() <= 0) {
                break;
            }
            System.out.println(log);
            step++;
        }

        return log;
    }
    public void processAction(Player human, Enemy enemy, int attackCode){
        human.setAttack(attackCode);
        enemy.setAttack(behaviorManager.getBehaviorFor(enemy)[patternIndex]);
        if(isPlayerTurn){
            combat.processMove(human, enemy, isPlayerTurn);
            isPlayerTurn = false;
        } else if(!isPlayerTurn){
            combat.processMove(enemy, human, isPlayerTurn);
            isPlayerTurn = true;
        }
        if(human.getHealth() <= 0 || enemy.getHealth() <= 0){
            System.out.println("игра окончена");
        }
        
        
    }
}
