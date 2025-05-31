/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.battle;

import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.ShaoKahn;
import java.util.Random;

/**
 *
 * @author nsoko
 */
//Отвечает за то, что происход  ит при взаимодействии двух участников (игрок vs враг) в одном ходе. 
public class CombatSystem {

    private boolean playerStunned;
    private boolean enemyStunned;
    private final Random random = new Random();
    private String attackerAction;
    private String defenderAction;

    //Обработка одного хода (атака/защита/пропуск)
    public boolean isPlayerStunned(){
        return this.playerStunned;
    }
    public boolean isEnemyStunned(){
        return this.enemyStunned;
    }
    
    public BattleResult processMove(Entity attacker, Entity defender, boolean isPlayerTurn) {


        int attackType = attacker.getAttack();
        int defendType = defender.getAttack();

        if (isPlayerTurn && playerStunned) {
            handlePlayerStunned(attacker, defender, defendType);
            return new BattleResult(attacker, defender, defendType, false, false, false);
        } else if (!isPlayerTurn && enemyStunned) {
            handleEnemyStunned(attacker, defender, defendType);
            return new BattleResult(attacker, defender, defendType, false, false, false);
        }

        return switch (attackType + "-" + defendType) {
            case "1-0" ->
                handleCounterattack(attacker, defender);
            case "1-1" ->
                handleBothAttack(attacker, defender);
            case "0-0" ->
                handleBothDefend(attacker, defender, isPlayerTurn);
            //пока не понятно
            case "0-1" ->
//                handleNoAction(attacker, defender);
                handleCounterattack(defender, attacker);
            case "-1-0", "-1-1" ->
                handleStunnedAttack(attacker, defender, isPlayerTurn);
            default ->
                new BattleResult(attacker, defender, 0, false, false, false);
        };
    }

    private void handleEnemyStunned(Entity attacker, Entity defender, int defendType) { //атакующий - враг и он оглушен, защищается игрок 
        if (defendType == 1) {
            int damage = defender.getDamage();
            attacker.setHealth(-damage);
            enemyStunned = false;
        }
        enemyStunned = false;
        System.out.println(attacker.getName() + " вышел из оглушения.");
    }

    private void handlePlayerStunned(Entity attacker, Entity defender, int defendType) {
        if (defendType == 1) {
            int damage = defender.getDamage();
            attacker.setHealth(-damage);
        }
        playerStunned = false;
        System.out.println(attacker.getName() + " вышел из оглушения.");
    }

//    private void applyStun(Entity attacker, Entity defender, boolean isPlayerTurn) {
//        if (isPlayerTurn) {
//            playerStunned = false;
//            System.out.println(attacker.getName() + " вышел из оглушения.");
//        } else {
//            enemyStunned = false;
//            System.out.println(attacker.getName() + " был оглушен.");
//        }
//    }
    private BattleResult handleCounterattack(Entity attacker, Entity defender) {
        int damage;
        boolean isCounterattack = true;

        if (attacker instanceof ShaoKahn && random.nextDouble() < 0.15) {
            // Шао Кан может сломать блок — контратака не срабатывает
            damage = (int) (attacker.getDamage() * 0.5);
            isCounterattack = false;
            defender.setHealth(-damage); // обычный урон по защищающемуся
        } else {
            // обычная контратака: защищающийся бьёт атакующего
            damage = (int) (defender.getDamage() * 0.5);
            attacker.setHealth(-damage); // урон наносится атакующему
            System.out.println(defender.getName() + " контратаковал " + attacker.getName());
        }
        BattleResult br = new BattleResult(defender, attacker, damage, isCounterattack, false, false);
        br.setAttackerAction("ЧЕ СЮДА ВСТАВЛЯТЬ");
        br.setDefenderAction("ЧЕ СЮДА ВСТАВЛЯТЬ");

        return new BattleResult(defender, attacker, damage, isCounterattack, false, false);
    }

    private BattleResult handleBothAttack(Entity attacker, Entity defender) {
        int damage = attacker.getDamage();
        defender.setHealth(-damage);
        return new BattleResult(attacker, defender, damage, false, false, false);
    }

    private BattleResult handleBothDefend(Entity attacker, Entity defender, boolean isPlayerTurn) {
        if (random.nextBoolean()) {
            if (isPlayerTurn) {
                enemyStunned = true;
            } else {
                playerStunned = true;
            }
//            if (attacker instanceof Player) {
//                playerStunned = true;
//            } else {
//                enemyStunned = true;
//            }
            return new BattleResult(attacker, defender, 0, false, true, false);
        } else {
            return new BattleResult(attacker, defender, 0, false, false, false);
        }
    }

    private BattleResult handleNoAction(Entity attacker, Entity defender) {
        System.out.println("так называемое ничего.");
        return new BattleResult(attacker, defender, 0, false, false, false);
    }

    private BattleResult handleStunnedAttack(Entity attacker, Entity defender, boolean isPlayerTurn) {
        int damage = defender.getDamage();
        attacker.setHealth(-damage);
        if (isPlayerTurn) {
            playerStunned = false;
        } else {
            enemyStunned = false;
        }
        return new BattleResult(defender, attacker, damage, false, false, false);
    }

    //Проверка завершения раунда
    public boolean isRoundOver(Entity human, Entity enemy) {
        return human.getHealth() <= 0 || enemy.getHealth() <= 0;
    }

    //Проверка завершения игры
    public boolean isGameOver(Player human) {
        return human.getWin() >= 11;
    }

    //Получение победителя
    public Entity getWinner(Player human, Enemy enemy) {
        return human.getHealth() > 0 ? human : enemy;
    }

}
