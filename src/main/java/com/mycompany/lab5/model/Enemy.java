package com.mycompany.lab5.model;

/**
 *
 * @author nsoko
 */
public class Enemy extends Entity {

    private boolean isWeakened = false;
    private int weakenTurns = 0;
    private static final double DAMAGE_INCREASE = 1.25; // 25%
    private static final double ATTACK_REDUCTION = 0.5;  // 50%
    //тип врага: танк солдат и тд

    public Enemy(int level, int health, int damage) {
        super(level, health, damage);
    }

    @Override
    public void setDamage(int damage) {
        if (isWeakened()) {
            damage *= DAMAGE_INCREASE; // Увеличенный урон из-за ослабления
            System.out.println("Урон увеличен на 25% из-за ослабления");
        }
    }

    public void applyWeakness(int duration) {
        this.isWeakened = true;
        this.weakenTurns = duration;
    }

    public boolean isWeakened() {
        return isWeakened;
    }

    public int getWeakenTurns() {
        return weakenTurns;
    }

    public void decreaseWeakenTurns() {
        if (weakenTurns > 0) {
            weakenTurns--;
            if (weakenTurns == 0) {
                isWeakened = false;
            }
        }
    }

    public void clearWeaken() {
        weakenTurns = 0;
        isWeakened = false;
    }

    // При атаке ослаблённый враг теряет дебаф
    public void attackWhileWeakened() {
        clearWeaken();
    }

}
