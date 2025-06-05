package com.mycompany.lab5.battle;

import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.model.Player;

/**
 *
 * @author nsoko
 */
public class LevelManager {

    private final int[] experience_for_next_level = {40, 90, 180, 260, 410, 1000};
    private boolean reachedNewLevel = false;

    public boolean isNewLevel() {
        return this.reachedNewLevel;
    }

    public int getExperienceForNextLevel(int level) {
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

    public void checkLevelUp(Player human, Entity[] enemies){
        if(human.getExperience() >= experience_for_next_level[human.getLevel()]){
            reachedNewLevel = true;
            human.setLevel();
            human.setNextExperience(experience_for_next_level[human.getLevel()]);
            updatePlayerStats(human);
            updateEnemiesStats(enemies, human);
        }
    }

    private void updatePlayerStats(Player human) {
        switch (human.getLevel()) {
            case 1 -> {
                human.updateMaxHealth(25);
                human.updateDamage(3);
            }
            case 2 -> {
                human.updateMaxHealth(30);
                human.updateDamage(3);
            }
            case 3 -> {
                human.updateMaxHealth(30);
                human.updateDamage(4);
            }
            case 4 -> {
                human.updateMaxHealth(40);
                human.updateDamage(6);
            }
        }
    }

    public void updateEnemiesStats(Entity[] enemies, Player human) {
        int hpBonusPercent = 0;
        int damageBonusPercent = 0;

        switch (human.getLevel()) {
            case 1 -> {
                hpBonusPercent = 10;
                damageBonusPercent = 10;
            }
            case 2 -> {
                hpBonusPercent = 15;
                damageBonusPercent = 15;
            }
            case 3 -> {
                hpBonusPercent = 20;
                damageBonusPercent = 20;
            }
            case 4 -> {
                hpBonusPercent = 25;
                damageBonusPercent = 25;
            }
            default -> {
                hpBonusPercent = 30;
                damageBonusPercent = 30;
            }
        }

        for (Entity entity : enemies) {
            if (entity != null) {
                entity.updateMaxHealth((int) (entity.getMaxHealth() * hpBonusPercent / 100.0));
                entity.updateDamage((int) (entity.getDamage() * damageBonusPercent / 100.0));
                entity.setLevel();
            }
        }
    }

}
