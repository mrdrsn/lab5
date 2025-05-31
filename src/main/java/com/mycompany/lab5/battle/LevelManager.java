
package com.mycompany.lab5.battle;

import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.model.Player;

/**
 *
 * @author nsoko
 */
public class LevelManager {
    private final int[] experience_for_next_level = {40, 90, 180, 260, 410, 1000};
    private boolean reachedNewLevel = false;
    
    public boolean isNewLevel(){
        return this.reachedNewLevel;
    }
    public int getExperienceForNextLevel(int level){
        return experience_for_next_level[level];
    }

    public void addExperience(Player human, Entity[] enemies) {
        reachedNewLevel = false;
        switch (human.getLevel()) {
            case 0 -> {
                human.setExperience(20);
                human.setPoints(25 + human.getHealth() / 4);
            }
            case 1 -> {
                human.setExperience(25);
                human.setPoints(30 + human.getHealth() / 4);
            }
            case 2 -> {
                human.setExperience(30);
                human.setPoints(35 + human.getHealth() / 4);
            }
            case 3 -> {
                human.setExperience(40);
                human.setPoints(45 + human.getHealth() / 4);
            }
            case 4 -> {
                human.setExperience(50);
                human.setPoints(55 + human.getHealth() / 4);
            }
        }
        checkLevelUp(human, enemies);
    }

    public void addBossExperience(Player human, Entity[] enemies) {
        reachedNewLevel = false;
        switch (human.getLevel()) {
            case 2 -> {
                human.setExperience(30);
                human.setPoints(45 + human.getHealth() / 2);
            }
            case 4 -> {
                human.setExperience(50);
                human.setPoints(65 + human.getHealth() / 2);
            }
        }

        checkLevelUp(human, enemies);
    }

    public void checkLevelUp(Player human, Entity[] enemies) {
        for (int i = 0; i < experience_for_next_level.length - 1; i++) {
            if (experience_for_next_level[i] == human.getExperience()) {
                reachedNewLevel = true;
                human.setLevel();
                human.setNextExperience(experience_for_next_level[i + 1]);
                updatePlayerStats(human);
                updateEnemiesStats(enemies, human);
            }
        }
    }

    private void updatePlayerStats(Player human) {
        switch (human.getLevel()) {
            case 1 -> {
                human.setMaxHealth(25);
                human.setDamage(3);
            }
            case 2 -> {
                human.setMaxHealth(30);
                human.setDamage(3);
            }
            case 3 -> {
                human.setMaxHealth(30);
                human.setDamage(4);
            }
            case 4 -> {
                human.setMaxHealth(40);
                human.setDamage(6);
            }
        }
    }

    private void updateEnemiesStats(Entity[] enemies, Player human) {
        int hp = 0;
        int damage = 0;

        switch (human.getLevel()) {
            case 1 -> {
                hp = 32;
                damage = 25;
            }
            case 2 -> {
                hp = 30;
                damage = 20;
            }
            case 3 -> {
                hp = 23;
                damage = 24;
            }
            case 4 -> {
                hp = 25;
                damage = 26;
            }
        }

        for (Entity enemy : enemies) {
            enemy.setMaxHealth((int) (enemy.getMaxHealth() * hp / 100.0));
            enemy.setDamage((int) (enemy.getDamage() * damage / 100.0));
            enemy.setLevel();
        }
    }

}
