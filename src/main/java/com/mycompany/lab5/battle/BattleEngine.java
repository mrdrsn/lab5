
package com.mycompany.lab5.battle;

import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;


//Здесь проигрывается весь бой с одним противником: ходы, изменение здоровья, применение предметов и т.д. 
public class BattleEngine {
    
    private final CombatSystem combat = new CombatSystem();
    private final EnemyBehaviorManager behaviorManager = new EnemyBehaviorManager();
    private int actionIndex = 0;
    private Enemy enemy = null;
    private int[] enemyPattern = behaviorManager.getBehaviorFor(enemy);
    private boolean isPlayerTurn = false;
    private boolean playerStunned = false;
    private boolean enemyStunned = false;
    private boolean battleOver = false;
    
    public BattleEngine(){
//        setEnemy();
    }
    
    public void setTurn(boolean playerTurn){
        this.isPlayerTurn = playerTurn;
    }
    
    public boolean getPlayerTurn(){
        return this.isPlayerTurn;
    }
    public boolean isBatlleOver(){
        return this.battleOver;
    }
    public void processAction(Player human, Enemy enemy, int attackCode){
        human.setAttack(attackCode);
        playerStunned = false;
        enemyStunned = false;
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
            this.battleOver = true;
        }
        for(int p: enemyPattern){
            System.out.print(p + " ");
        }
        if(combat.isPlayerStunned()){
            this.playerStunned = true;
        } else if (combat.isEnemyStunned()){
            this.enemyStunned = true;
        }
    }
    

    public boolean isPlayerStunned(){
        return this.playerStunned;
    }
    public boolean isEnemyStunned(){
        return this.enemyStunned;
    }
    public Enemy getEnemy(){
        return this.enemy;
    }
    public void setEnemy(Enemy enemy){
        this.enemy = enemy;
    }
}