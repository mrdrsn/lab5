package com.mycompany.lab5.battle;

import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.ShaoKahn;
import java.util.Random;

public class CombatSystem {

    private boolean isPlayerTurn; // true — ход игрока, false — врага

    private boolean playerStunned;
    private boolean enemyStunned;

    private boolean playerDebuffed;
    private boolean enemyDebuffed;

    private int playerDebuffedTurns;
    private int enemyDebuffedTurns;

    private final Random random = new Random();

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public boolean isPlayerStunned() {
        return playerStunned;
    }

    public boolean isEnemyStunned() {
        return enemyStunned;
    }

    public boolean isPlayerDebuffed() {
        return playerDebuffed;
    }

    public boolean isEnemyDebuffed() {
        return enemyDebuffed;
    }

    public void processMove(Entity attacker, Entity defender) {
        int attackerAction = attacker.getAttack();
        int defenderAction = defender.getAttack();

        if (isPlayerTurn && playerStunned) {
            handlePlayerStunned(attacker, defender);
            playerStunned = false;
            return;
        } else if (!isPlayerTurn && enemyStunned) {
            handleEnemyStunned(attacker, defender);
            enemyStunned = false;
            return;
        }

        if (playerDebuffed) {
            //после наложения дебаффа следующий ход - игрока
            if (isPlayerTurn && playerDebuffedTurns == defender.getLevel()) {
                System.out.println("1й ход игрока под дебаффом из " + playerDebuffedTurns);
                handlePlayerDebuffed(attacker, defender);
            }
            playerDebuffedTurns--;
            if (playerDebuffedTurns == 0) {
                handlePlayerDebuffed(attacker, defender);
            }
        }
        
        if(enemyDebuffed){
            if(!isPlayerTurn && enemyDebuffedTurns == defender.getLevel()) {
                System.out.println("1й ход врага под дебаффом из " + enemyDebuffedTurns);
                handleEnemyDebuffed(attacker, defender);
            }
            enemyDebuffedTurns--;
            if (enemyDebuffedTurns == 0){
                handleEnemyDebuffed(attacker, defender);
            }
        }

        String moveKey = attackerAction + "-" + defenderAction;

        switch (moveKey) {
            case "1-1": // Атака vs Атака
                handleBothAttack(attacker, defender);
                break;

            case "1-0": // Атака vs Защита
                handleAttackDefend(attacker, defender);
                break;

            case "0-1": // Защита vs Атака
                handleDefendAttack(attacker, defender);
                break;

            case "0-0": // Защита vs Защита
                handleBothDefend(attacker, defender);
                break;

            case "2-0": // Ослабление vs Защита
                handleDebuffDefend(attacker, defender);
                break;

            case "2-1": // Ослабление vs Атака
                handleDebuffAttack(attacker, defender);
                break;

            default:
                System.out.println("Неизвестная комбинация действий: " + moveKey);
                break;
        }
    }

    //attacker - Player, Defender - Enemy
    private void handlePlayerDebuffed(Entity attacker, Entity defender) {

        if (playerDebuffedTurns == defender.getLevel()) {
            attacker.setDamage((int) (attacker.getDamage() / 2));
            defender.setDamage((int) (defender.getDamage() * 1.25));
        }
        if (playerDebuffedTurns == 0) {
            playerDebuffed = false;
            if (attacker instanceof Player) {
                
                System.out.println(attacker.getName() + " вышел из ослабленного состояния! ");
                attacker.setDebuffed(false);
                attacker.setDamage(attacker.getDamage() * 2);
                defender.setDamage((int) (defender.getDamage() / 1.25));
            } else{
                System.out.println(defender.getName() + " вышел из ослабленного состояния! ");
                defender.setDebuffed(false);
                defender.setDamage(attacker.getDamage() * 2);
                attacker.setDamage((int) (defender.getDamage() / 1.25));
            }
        }
    }

    private void handleEnemyDebuffed(Entity attacker, Entity defender) { //attacker - Enemy, Defender - Player
        
        if (enemyDebuffedTurns == defender.getLevel()) {
            attacker.setDamage((int) (attacker.getDamage() / 2));
            defender.setDamage((int) (defender.getDamage() * 1.25));
        }
        if (enemyDebuffedTurns == 0) {
            enemyDebuffed = false;
            if (attacker instanceof Enemy) {
                System.out.println(attacker.getName() + " вышел из ослабленного состояния! ");
                attacker.setDebuffed(false);
                attacker.setDamage(attacker.getDamage() * 2);
                defender.setDamage((int) (defender.getDamage() / 1.25));
            } else{
                System.out.println(defender.getName() + " вышел из ослабленного состояния! ");
                defender.setDebuffed(false);
                defender.setDamage(attacker.getDamage() * 2);
                attacker.setDamage((int) (defender.getDamage() / 1.25));
            }
        }
    }

    private void handleDebuffDefend(Entity attacker, Entity defender) {
        if (attacker.getLevel() > 0 && !attacker.isStunned()) {
            double chance = random.nextDouble();
            if (chance < 0.75) {
                if (defender instanceof Player) {
                    playerDebuffed = true;
                    playerDebuffedTurns = attacker.getLevel();
                } else {
                    enemyDebuffed = true;
                    enemyDebuffedTurns = attacker.getLevel();
                }
                defender.setDebuffed(true);

                System.out.println("успешно ослабил" + defender.getName() + "на " + attacker.getLevel() + " ходов");
            } else {
                System.out.println(attacker.getName() + "безуспешно пытался ослабить " + defender.getName());
            }
        } else {
            System.out.println("вам пока рано ослаблять врагов. или вы оглушены.");
        }
    }
    
    private void handleDebuffAttack(Entity attacker, Entity defender){
        if(attacker.isStunned()){
            System.out.println("вы оглушены. ослабление не прошло. по вам нанесли повышенный урон.");
        } else{
            System.out.println("ослабление сорвалось. был нанесен повышенный урон: " + defender.getDamage()*1.15);
        }
        attacker.setHealth((int) (-defender.getDamage()*1.15));
    }

    private void handleBothAttack(Entity attacker, Entity defender) {
        defender.setHealth(-attacker.getDamage());
//        attacker.setHealth(-defender.getDamage());
        System.out.println(attacker.getName() + " и " + defender.getName() + " одновременно атаковали.");
    }

    private void handleAttackDefend(Entity attacker, Entity defender) {
        // Если это Шао Кан, то есть шанс сломать защиту
        if (defender instanceof ShaoKahn && random.nextDouble() < 0.15) {
            attacker.setHealth(-(int) (defender.getDamage() * 0.5));
            System.out.println("Шао Кан пробил защиту и контратаковал!");
        } else {
//            defender.setHealth(-attacker.getDamage());
            attacker.setHealth(-(int) (defender.getDamage() * 0.5));
            System.out.println(defender.getName() + " заблокировал и контратаковал.");
        }
    }

    private void handleDefendAttack(Entity attacker, Entity defender) {
        defender.setHealth(-(int) (attacker.getDamage() * 0.5));
//        attacker.setHealth(-(int)(defender.getDamage() * 0.5));
        System.out.println(attacker.getName() + " заблокировал и контратаковал.");
    }

    private void handleBothDefend(Entity attacker, Entity defender) {
        if (random.nextBoolean()) {
            if (attacker instanceof Player) {
                enemyStunned = true;
                System.out.println("Враг оглушён после двойной защиты.");
            } else {
                playerStunned = true;
                System.out.println("Игрок оглушён после двойной защиты.");
            }
        } else {
            System.out.println("Оба продолжают защищаться.");
        }
    }

    private void handlePlayerStunned(Entity attacker, Entity defender) { //attacker -> player
        if (defender.getAttack() == 1) {
            attacker.setHealth(-defender.getDamage());
        }
        System.out.println(attacker.getName() + " пропускает ход из-за оглушения.");
    }

    private void handleEnemyStunned(Entity attacker, Entity defender) { //attacker -> enemy
        if (defender.getAttack() == 1) {
            attacker.setHealth(-defender.getDamage());
        }
        System.out.println(attacker.getName() + " пропускает ход из-за оглушения.");
    }

}
