package com.mycompany.lab5;

import javax.swing.JDialog;

/**
 *
 * @author Мария
 */
public class CharacterAction {

//    private final int experience_for_next_level[] = {40, 90, 180, 260, 410, 1000};

//    private final int kind_fight[][] = {{1, 0}, {1, 1, 0}, {0, 1, 0}, {1, 1, 1, 1}};

    private final Enemy enemies[] = new Enemy[6];

    private Enemy enemy = null;
    public CharacterAction(){
        setEnemyes();
    }
    private void setEnemyes() {
        enemies[0] = new BarakaFactory().createEnemy();
        enemies[1] = new SubZeroFactory().createEnemy();
        enemies[2] = new LiuKangFactory().createEnemy();
        enemies[3] = new SonyaBladeFactory().createEnemy();
        enemies[4] = new ShaoKahnFactory().createEnemy();
        //другой shao kahn
        enemies[5] = new ShaoKahnFactory().createEnemy();
    }

    public Enemy[] getEnemyes() {
        return this.enemies;
    }


    public Enemy ChooseEnemy() {
        int i = (int) (Math.random() * 4);
        switch (i) {
            case 0 -> {
                enemy = enemies[0];
            }
            case 1 -> {
                enemy = enemies[1];
            }
            case 2 -> {
                enemy = enemies[2];
            }
            case 3 -> {
                enemy = enemies[3];
            }
        }
        return enemy;
    }

    
    public Enemy ChooseBoss(int level){
        switch (level) {
            case 2 -> enemy = enemies[4];
            case 4 -> //ShaoKahn посильнее
                enemy = enemies[5];
        }
        return enemy;
    }


    public void addItems(int k1, int k2, int k3, Items[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    public void useItem(Entity human, Items[] items, String name, JDialog dialog, JDialog dialog1) {
        switch (name) {
            case "jRadioButton1":
                if (items[0].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton2":
                if (items[1].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton3":
                dialog.setVisible(true);
                dialog.setBounds(300, 200, 400, 300);
                break;
        }
        
        if(dialog.isVisible()==false){
            dialog1.dispose();
        }
    }
}
