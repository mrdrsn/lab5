    package com.mycompany.lab5.battle;

import com.mycompany.lab5.enemy.Baraka;
import com.mycompany.lab5.model.Enemy;
import com.mycompany.lab5.enemy.LiuKang;
import com.mycompany.lab5.enemy.SonyaBlade;
import com.mycompany.lab5.enemy.SubZero;
import com.mycompany.lab5.enemy.ShaoKahn;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyBehaviorManager {

    private final Random random = new Random();
    private final List<Integer> playerActionHistory = new ArrayList<>(5); // Храним последние 5 действий
    private static final int HISTORY_SIZE = 5;
    private boolean isPlayerTurn;

    public void isPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public void registerPlayerAction(int action) {
        if (playerActionHistory.size() >= HISTORY_SIZE) {
            playerActionHistory.remove(0);
        }
        playerActionHistory.add(action);
    }

    public int getBehavior(Enemy enemy) {
        double chance = random.nextDouble();

        // Анализируем поведение игрока
        int attackCount = 0;
        int defendCount = 0;

        for (int action : playerActionHistory) {
            if (action == 1) {
                attackCount++;
            } else if (action == 0) {
                defendCount++;
            }
        }

        boolean isSpammingAttack = attackCount >= 4 && defendCount <= 1;
        boolean isSpammingDefend = defendCount >= 4 && attackCount <= 1;

        if (enemy instanceof Baraka) {
            return adaptiveBaraka(chance, isSpammingAttack, isSpammingDefend);
        } else if (enemy instanceof SubZero) {
            return adaptiveSubZero(chance, isSpammingAttack, isSpammingDefend);
        } else if (enemy instanceof LiuKang) {
            return adaptiveLiuKang(chance, isSpammingAttack, isSpammingDefend);
        } else if (enemy instanceof SonyaBlade) {
            return adaptiveSonyaBlade(chance, isSpammingAttack, isSpammingDefend);
        } else if (enemy instanceof ShaoKahn) {
            return adaptiveShaoKahn(chance, isSpammingAttack, isSpammingDefend);
        }

        return random.nextInt(2); // по умолчанию случайное действие
    }

    // === Адаптивные стратегии для каждого типа врага ===
    private int adaptiveBaraka(double chance, boolean isSpammingAttack, boolean isSpammingDefend) {
        if (isSpammingAttack) {
            return 0; // Защищаемся от постоянной атаки
        } else if (isSpammingDefend) {
            return 0; // Защита ради шанса оглушить
        } else if (chance < 0.6) {
            return 1; // Атака
        } else {
            return 0; // Защита
        }
    }

    private int adaptiveSubZero(double chance, boolean isSpammingAttack, boolean isSpammingDefend) {
        if (!isPlayerTurn) {
            if (isSpammingAttack) {
                return 0;
            } else if (isSpammingDefend) {
                return 0;
            } else if (chance < 0.3) {
                return 1;
            } else if (chance < 0.6 && chance >= 0.3) {
                return 2;
            } else {
                return 0;
            }
        } else {
            if (isSpammingAttack) {
                return 0;
            } else if (isSpammingDefend) {
                return 0;
            } else if (chance < 0.6) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private int adaptiveLiuKang(double chance, boolean isSpammingAttack, boolean isSpammingDefend) {
        if (isSpammingAttack) {
            return 0; // Ответная защита
        } else if (isSpammingDefend) {
            return 0; // Используем защиту ради оглушения
        } else if (chance < 0.5) {
            return 1; // Атака
        } else {
            return 0; // Защита
        }
    }

    private int adaptiveSonyaBlade(double chance, boolean isSpammingAttack, boolean isSpammingDefend) {
        if (isSpammingAttack) {
            return 0; // Блокируем
        } else if (isSpammingDefend) {
            return 0; // Пробуем оглушить
        } else if (chance < 0.6) {
            return 1; // Атака
        } else {
            return 0; // Защита
        }
    }

    private int adaptiveShaoKahn(double chance, boolean isSpammingAttack, boolean isSpammingDefend) {
        
        if (chance < 0.1) {
            return -1; // Регенерация здоровья
        } else if (isSpammingAttack) {
            return 0; // Блокировка
        } else if (isSpammingDefend) {
            return 0; // Защита ради оглушения
        } else if (chance < 0.45) {
            return 1; // Атака
        } else {
            return 0; // Защита
        }
    }
}
