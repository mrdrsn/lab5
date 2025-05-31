package com.mycompany.lab5.battle;

import com.mycompany.lab5.enemy.Baraka;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.LiuKang;
import com.mycompany.lab5.enemy.SonyaBlade;
import com.mycompany.lab5.enemy.SubZero;
import com.mycompany.lab5.enemy.ShaoKahn;
import java.util.Random;

/**
 *
 * @author nsoko
 */
public class EnemyBehaviorManager {

    private final Random random = new Random();

    // Базовые паттерны поведения
    private final int[][] behaviorPattern = {
        {1, 0}, // Паттерн 1: Атака -> Защита
        {1, 1, 0}, // Паттерн 2: Атака -> Атака -> Защита
        {0, 1, 0}, // Паттерн 3: Защита -> Атака -> Защита
        {1, 1, 1, 1} // Паттерн 4: Только атаки
    };
    public int[] getBehaviorFor(Enemy enemy) {
        double chance = random.nextDouble(); // случайное число от 0 до 1

        if (enemy instanceof Baraka) {
            return chooseBehavior(chance, 0.15, 0.15, 0.60); // 15%, 15%, 60%
        } else if (enemy instanceof SubZero) {
            return chooseBehavior(chance, 0.25, 0.25, 0.00); // 25%, 25%, 0%
        } else if (enemy instanceof LiuKang) {
            return chooseBehavior(chance, 0.13, 0.13, 0.10); // 13%, 13%, 10%
        } else if (enemy instanceof SonyaBlade) {
            return chooseBehavior(chance, 0.25, 0.25, 0.50); // 25%, 25%, 50%
        } else if (enemy instanceof ShaoKahn) {
            return chooseBehavior(chance, 0.10, 0.45, 0.00); // 10%, 45%, 0%
        }
        return behaviorPattern[0];
    }
    
    //Выбирает поведение на основе вероятностей k1, k2, k3
     
    private int[] chooseBehavior(double chance, double k1, double k2, double k3) {
        if (chance < k1) {
            return behaviorPattern[0]; // 1 тип поведения
        } else if (chance < k1 + k2) {
            return behaviorPattern[1]; // 2 тип
        } else if (chance < k1 + k2 + k3) {
            return behaviorPattern[2]; // 3 тип
        } else {
            return behaviorPattern[3]; // 4 тип
        }
    }

}
