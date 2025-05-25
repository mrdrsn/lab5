package com.mycompany.lab5;

//ADD IMAGE!!!
import java.util.ArrayList;

/**
 *
 * @author Мария
 */
public class Fight {
    
    int kind_attack[] = {0};
//    int experiences[] = {40, 90, 180, 260, 410};
    int i = 1; //??? номер хода (в зависимости от четности нечетности)
    int k = -1; //???
    int stun = 0;
    double v = 0.0;

    public void Move(Entity p1, Entity p2) {
        if (stun == 1) {
            p1.setAttack(-1);
        }
        switch (Integer.toString(p1.getAttack()) + Integer.toString(p2.getAttack())) {
            case "10" -> {
                v = Math.random();
                if (p1 instanceof ShaoKahn & v < 0.15) {
                    p2.setHealth(-(int) (p1.getDamage() * 0.5));
                    System.out.println("Your block is broken");

                } else {
                    p1.setHealth(-(int) (p2.getDamage() * 0.5));
                    System.out.println(p2.getName() + " counterattacked");
                }
            }
            case "11" -> {
                p2.setHealth(-p1.getDamage());
                System.out.println(p1.getName() + " attacked");
            }
            case "00" -> {
                v = Math.random();
                if (v <= 0.5) {
                    stun = 1;
                }
                System.out.println("Both defended themselves");
            }
            case "01" -> {
                System.out.println(p1.getName() + " didn't attacked");
            }
            case "-10" -> {
                System.out.println(p1.getName() + " was stunned");
                stun = 0;
                System.out.println(p2.getName() + " didn't attacked");
            }
            case "-11" -> {
                p1.setHealth(-p2.getDamage());
                System.out.println(p1.getName() + " was stunned");
                stun = 0;
                System.out.println(p2.getName() + " attacked");
            }
        }
    }

    public void Hit(Player human, Enemy enemy, int a, CharacterAction action, EnemyBehaviorManager behavior,
            ArrayList<Result> results, Items[] items) {
//        label7.setText("");
        human.setAttack(a);

        if (k < kind_attack.length - 1) {
            k++;
        } else {
            kind_attack = behavior.getBehaviorFor(enemy);
            k = 0;
        }
        enemy.setAttack(kind_attack[k]);
        if (i % 2 == 1) {
            Move(human, enemy);
        } else {
            Move(enemy, human);
        }
        i++;
        //обновление програсс бара
//        action.HP(human, pr1);
//        action.HP(enemy, pr2);

        //применение возрождения
        if (human.getHealth() <= 0 & items[2].getCount() > 0) {
            human.setNewHealth((int) (human.getMaxHealth() * 0.05));
            items[2].setCount(-1);
//            action.HP(human, pr1);
            System.out.println(human.getHealth() + "/" + human.getMaxHealth());
            //обновление количества возрождений
            System.out.println(items[2].getName() + ", " + items[2].getCount() + " шт");
            System.out.println("Вы воскресли");
        }
        //если здоровье одного из игроков достигло 0 или отрицательного значения,то
        //вызывается метод endRound или endFinalRound
        if (human.getHealth() <= 0 | enemy.getHealth() <= 0) {
            if (human.getWin() == 11) {
                endFinalRound(human, action, results);
            } else {
                endRound(human, enemy, action, items);
            }
        }
    }

    public void endRound(Player human, Enemy enemy,
            CharacterAction action, Items[] items) {
//        dialog.setVisible(true);
//        dialog.setBounds(300, 150, 700, 600);
        if (human.getHealth() > 0) {
            System.out.println("You win");
//            label.setText("You win");
            human.setWin();
            //если это была победа над боссом (поменять на instanceof Boss)
            if (enemy instanceof ShaoKahn) {
                action.addItems(38, 23, 8, items);
//                LevelManager.addBossExperience(human, action.getEnemyes());
            } else {
                action.addItems(25, 15, 5, items);
//                LevelManager.addExperience(human, action.getEnemyes());
            }
        } else {
            System.out.println(enemy.getName() + "wins");
            //label.setText(enemy.getName() + " win");
        }
        
        //сброс параметров? 
        
        i = 1;
        k = -1;
        kind_attack = ResetAttack();

    }

    public void endFinalRound(Player human, CharacterAction action,
            ArrayList<Result> results) {
        String text = "Победа не на вашей стороне";
        if (human.getHealth() > 0) {
            human.setWin();
//            LevelManager.addExperience(human, action.getEnemyes());
            text = "Победа на вашей стороне";
        }
        boolean top = false;
        if (results == null) {
            top = true;
        } else {
            int iLocal = 0;
            for (int j = 0; j < results.size(); j++) {
                if (human.getPoints() < results.get(j).getPoints()) {
                    iLocal++;
                }
            }
            if (iLocal < 10) {
                top = true;
            }
        }
//        if (top) {
//            dialog1.setVisible(true);
//            dialog1.setBounds(150, 150, 600, 500);
//            label1.setText(text);
//        } else {
//            dialog2.setVisible(true);
//            dialog2.setBounds(150, 150, 470, 360);
//            label2.setText(text);
//        }
//        frame.dispose();
    }

    public int[] ResetAttack() {
        int a[] = {0};
        return a;
    }

    public Enemy newRound(Player human, CharacterAction action) {
        Enemy enemy = null;
        if (human.getWin() == 6 | human.getWin() == 11) {
            enemy= action.ChooseBoss(human.getLevel());
        } else {
            enemy = action.ChooseEnemy();
        }
//        pr1.setMaximum(human.getMaxHealth());
//        pr2.setMaximum(enemy.getMaxHealth());
        human.setNewHealth(human.getMaxHealth());
        enemy.setNewHealth(enemy.getMaxHealth());
//        action.HP(human, pr1);
//        action.HP(enemy1, pr2);
        return enemy;
    }

}
