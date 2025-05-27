
package com.mycompany.lab5;


//Здесь проигрывается весь бой с одним противником: ходы, изменение здоровья, применение предметов и т.д. 
public class BattleEngine {
    
    private final CombatSystem combat = new CombatSystem();
    private final EnemyBehaviorManager behaviorManager = new EnemyBehaviorManager();
//    private final ItemManager itemManager = new ItemManager();
    private final CharacterAction enemies = new CharacterAction();
    private int actionIndex = 0;
    private Enemy enemy = null;
    private int[] enemyPattern = behaviorManager.getBehaviorFor(enemy);
    private boolean isPlayerTurn = true;
    
    public BattleEngine(){
        setEnemy();
    }
    
    public void processAction(Player human, Enemy enemy, int attackCode){
        human.setAttack(attackCode);
        
        //логика перевыбора паттерна поведения
        if (actionIndex > enemyPattern.length - 1){
            actionIndex = 0;
            enemyPattern = behaviorManager.getBehaviorFor(enemy);
        }
        enemy.setAttack(enemyPattern[actionIndex]);
        actionIndex++;
        
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
        for(int p: enemyPattern){
            System.out.print(p + " ");
        }
    }
    public Enemy getEnemy(){
        return this.enemy;
    }
    public void setEnemy(){
        this.enemy = enemies.ChooseEnemy();
//        this.enemy = enemies.getEnemyes()[index];
    }
}

//    public BattleLog fight(Player human, Enemy enemy) {
//        BattleLog log = new BattleLog(human, enemy);
//
//        int[] enemyPattern = behaviorManager.getBehaviorFor(enemy);
////        for(int)
//        int step = 0;
//
//        while (!log.isOver()) {
//            System.out.println(step);
//            int enemyAttack = enemyPattern[step % enemyPattern.length];
//            enemy.setAttack(enemyAttack);
//
//            // Ход игрока
//            BattleResult result = combat.processMove(human, enemy, true);
//            log.addResult(result);
//            if (enemy.getHealth() <= 0 || human.getHealth() <= 0) {
//                break;
//            }
//
//            // Ход противника
//            result = combat.processMove(enemy, human, false);
//            log.addResult(result);
//
//            if (enemy.getHealth() <= 0 || human.getHealth() <= 0) {
//                break;
//            }
//            System.out.println(log);
//            step++;
//        }
//
//        return log;
//    }