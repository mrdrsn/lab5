/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.ui;

import com.mycompany.lab5.battle.GameEngine;
import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.model.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nastya
 */
public class GUIHelper {
    
    public GUIDemo gui;
    
    public GUIHelper(GUIDemo gui){
        this.gui = gui;
    }
    
    public JPanel createTopPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("FIGHT");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        topPanel.add(titleLabel);
        return topPanel;
    }
    public JPanel createBottomPanel(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.add(gui.getItemsButton());
        bottomPanel.add(gui.getAttackButton());
        bottomPanel.add(gui.getDefendButton());
        bottomPanel.add(gui.getDebuffButton());

        return bottomPanel;
    }
    public JPanel createCenterPanel(Entity enemy, Entity player){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(createCharacterPanel(enemy));   // левая панель (враг)
        panel.add(createInfoPanel());             // центральная информация
        panel.add(createCharacterPanel(player));   // правая панель (игрок)
        return panel;
    }
    
    public JPanel createCharacterPanel(Entity entity){
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));

        JPanel healthPanel = new JPanel(new BorderLayout());
        healthPanel.setPreferredSize(new Dimension(300, 45));
        JProgressBar healthBar = new JProgressBar(0, entity.getMaxHealth());
        healthBar.setForeground(Color.green);
        healthBar.setValue(entity.getHealth());

        JLabel healthLabel = new JLabel(entity.getHealth() + "/" + entity.getMaxHealth());
        JLabel damageLabel = new JLabel("Урон " + entity.getDamage());

        healthPanel.add(healthLabel, BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        healthPanel.add(damageLabel, BorderLayout.EAST);

        if (entity.equals(gui.getHuman())) {
            gui.setPlayerHealthBar(healthBar);
            gui.setPlayerHealthLabel(healthLabel);
            gui.setPlayerDamageLabel(damageLabel);
            gui.setPlayerNameLabel(new JLabel(entity.getName()));
            gui.setPlayerLevelLabel(new JLabel(entity.getLevel() + " уровень"));
            gui.setPlayerImagePanel(new BackgroundPanel("Китана.jpg"));

        } else {
            gui.setEnemyHealthBar(healthBar);
            gui.setEnemyHealthLabel(healthLabel);
            gui.setEnemyDamageLabel(damageLabel);
            gui.setEnemyNameLabel(new JLabel(entity.getName()));
            gui.setEnemyLevelLabel(new JLabel(entity.getLevel() + " уровень"));
            gui.setEnemyImagePanel(new BackgroundPanel(entity.getName() + ".jpg"));
        }

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setPreferredSize(new Dimension(300, 30));
        infoPanel.setBackground(Color.YELLOW);
        JLabel nameLabel = entity.equals(gui.getHuman()) ? gui.getPlayerNameLabel() : gui.getEnemyNameLabel();
        JLabel levelLabel = entity.equals(gui.getHuman()) ? gui.getPlayerLevelLabel() : gui.getEnemyLevelLabel();
        infoPanel.add(nameLabel);
        infoPanel.add(levelLabel);
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(new Dimension(300, 15));
        JPanel imagePanel = entity.equals(gui.getHuman()) ? gui.getPlayerImagePanel() : gui.getEnemyImagePanel();
        imagePanel.setPreferredSize(new Dimension(300, 610));
        characterPanel.add(healthPanel);
        characterPanel.add(blankPanel);
        characterPanel.add(imagePanel);
        characterPanel.add(infoPanel);
        System.out.println(imagePanel.getSize());

        return characterPanel;
    }
    
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        JPanel expPanel = new JPanel(new GridLayout(2, 2));
        expPanel.setPreferredSize(new Dimension(300, 100));

        JLabel pointsLabel = new JLabel("points");
        JLabel expLabel = new JLabel("experience");
        gui.setPointsValueLabel(new JLabel("00"));
        gui.setExpPointsValueLabel(new JLabel("00/40"));

        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gui.getPointsValueLabel().setHorizontalAlignment(SwingConstants.CENTER);
        expLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gui.getExpValueLabel().setHorizontalAlignment(SwingConstants.CENTER);

        expPanel.add(pointsLabel);
        expPanel.add(expLabel);
        expPanel.add(gui.getPointsValueLabel());
        expPanel.add(gui.getExpValueLabel());
        
        gui.setTurnPanel(new JPanel(new GridLayout(5, 1)));
        gui.getTurnPanel().setPreferredSize(new Dimension(300, 400));
        gui.getTurnPanel().setBackground(Color.red);
        JLabel playerAction = new JLabel("");
        JLabel enemyAction = new JLabel("");
        JLabel turn = new JLabel("Ход противника");
        JLabel counterattack = new JLabel("");
        JLabel stun = new JLabel("");

        gui.setPlayerActionLabel(playerAction);
        gui.setEnemyActionLabel(enemyAction);
        gui.setTurnLabel(turn);
        gui.setCounterattackLabel(counterattack);
        gui.setStunLabel(stun);

        playerAction.setHorizontalAlignment(SwingConstants.CENTER);
        enemyAction.setHorizontalAlignment(SwingConstants.CENTER);
        turn.setHorizontalAlignment(SwingConstants.CENTER);
        counterattack.setHorizontalAlignment(SwingConstants.CENTER);
        stun.setHorizontalAlignment(SwingConstants.CENTER);

        gui.getTurnPanel().add(turn);
        gui.getTurnPanel().add(playerAction);
        gui.getTurnPanel().add(enemyAction);
        gui.getTurnPanel().add(counterattack);
        gui.getTurnPanel().add(stun);

        JPanel blankPanel = new JPanel(new GridLayout(2, 2));
        JLabel locationLabel = new JLabel("Локация: ");
        JLabel locationCountLabel = new JLabel(gui.getGameEngine().getCurrentLocationNumber() + " из " + gui.getGameEngine().getLocationNumber());
        JLabel locationMonsterLabel = new JLabel("Осталов врагов в текущей локации: ");
        JLabel locationMonsterCountLabel = new JLabel("Х из У");

        blankPanel.setBackground(Color.cyan);
        blankPanel.setPreferredSize(new Dimension(300, 100));
        blankPanel.add(locationLabel);
        blankPanel.add(locationCountLabel);
        blankPanel.add(locationMonsterLabel);
        blankPanel.add(locationMonsterCountLabel);

        infoPanel.add(expPanel);
        infoPanel.add(blankPanel);
        infoPanel.add(gui.getTurnPanel());

        return infoPanel;
    }
}
