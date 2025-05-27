package com.mycompany.lab5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import newpackage.BackgroundPanel;

/**
 *
 * @author nsoko
 */
public class GUIDemo extends JFrame {

    public GameEngine game = new GameEngine();
    public Player human = new Player();
    public CharacterAction action = new CharacterAction();
    public BattleEngine battleEngine = new BattleEngine();
    public EnemyBehaviorManager behaviorManager = new EnemyBehaviorManager();
    public Enemy firstEnemy = battleEngine.getEnemy();

    public GUIDemo() {
        JPanel startPanel = new JPanel(new BorderLayout());

        JButton startGameButton = new JButton("Начать новую игру");
        JButton showTableButton = new JButton("Просмотреть таблицу результатов");

        startPanel.add(startGameButton, BorderLayout.NORTH);
        startPanel.add(showTableButton, BorderLayout.SOUTH);

        startGameButton.addActionListener((ActionEvent e) -> {
            chooseName();
//            startGame();
//            this.dispose();
        });

        showTableButton.addActionListener((ActionEvent e) -> {
            openDialog();
        });

        setBounds(150, 50, 400, 300);
        setContentPane(startPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void chooseName() {
        try {
            String playerName = JOptionPane.showInputDialog("Введите имя").trim();
            if (playerName.equals("")) {
                JOptionPane.showMessageDialog(this, "Введите имя!!!");
            } else {
                human.setName(playerName);
                this.dispose();
                startGame();
            }
        } catch (NullPointerException ex) {
            System.out.println("Имя не было введено. Возвращаемся на стартовый экран");
            setVisible(true);
        }
    }

    private void startGame() {
        JFrame gameFrame = new JFrame();
        gameFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("FIGHT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        gameFrame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        JPanel leftPanel = createCharacterPanel(firstEnemy); //заменить имя на enemy
        leftPanel.setBackground(Color.ORANGE);

        JPanel middlePanel = createInfoPanel();
        middlePanel.setBackground(Color.CYAN);

        JPanel rightPanel = createCharacterPanel(human);
        rightPanel.setBackground(Color.RED);

        centerPanel.add(leftPanel);
        centerPanel.add(middlePanel);
        centerPanel.add(rightPanel);

        gameFrame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton itemsButton = new JButton("Предметы");
        JButton attackButton = new JButton("Атаковать");
        JButton defendButton = new JButton("Защититься");

        JButton changeEnemyButton = new JButton("Сменить противника");
        changeEnemyButton.addActionListener((ActionEvent e) -> {
            battleEngine.setEnemy();
            firstEnemy = battleEngine.getEnemy();
            System.out.println("");
            System.out.println("Новая игра! Ваш противник - " + firstEnemy.getName());
            System.out.println("");
            updatePanel(leftPanel, firstEnemy);
            leftPanel.revalidate();
            leftPanel.repaint();
        });
        attackButton.addActionListener((ActionEvent e) -> {
            battleEngine.processAction(human, firstEnemy, 1);
        });

        bottomPanel.add(itemsButton);
        bottomPanel.add(attackButton);
        bottomPanel.add(defendButton);
        bottomPanel.add(changeEnemyButton);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);

        gameFrame.setTitle("Лабораторная работа 5");
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setBounds(300, 100, 900, 600);
        gameFrame.setVisible(true);
    }

    private void updatePanel(JPanel panel, Entity newEntity) {
        panel = createCharacterPanel(newEntity);
//        return panel;
    }

    private JPanel createCharacterPanel(Entity entity) {
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout());

        // Верхняя часть: индикатор здоровья
        JPanel healthPanel = new JPanel();

        healthPanel.setBackground(Color.red);
        healthPanel.setPreferredSize(new Dimension(300, 45));
        healthPanel.setLayout(new BorderLayout());

        JProgressBar healthBar = new JProgressBar(0, entity.getMaxHealth());
        healthBar.setForeground(Color.green);
        healthBar.setValue(entity.getHealth()); // Пример значения

        JLabel healthLabel = new JLabel(Integer.toString(entity.getHealth()) + "/" + Integer.toString(entity.getMaxHealth()));
        String damageText = "Урон " + Integer.toString(entity.getDamage());
        healthPanel.add(healthLabel, BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        healthPanel.add(new JLabel(damageText), BorderLayout.EAST);
        characterPanel.add(healthPanel, BorderLayout.NORTH);
        String imagePath;
        if (entity.equals(human)) {
            imagePath = "Китана.jpg";
        } else {
            imagePath = entity.getName() + ".jpg";
        }

        JPanel imagePanel = new BackgroundPanel(imagePath);
        characterPanel.add(imagePanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.YELLOW);
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoPanel.add(new JLabel(entity.getName()));
        infoPanel.add(new JLabel(Integer.toString(entity.getLevel()) + " уровень"));
        characterPanel.add(infoPanel, BorderLayout.SOUTH);

        return characterPanel;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();

        JPanel expPanel = new JPanel(new GridLayout(2, 2));
        expPanel.setPreferredSize(new Dimension(300, 100));

        JLabel pointsLabel = new JLabel("points");
        JLabel pointsValue = new JLabel("00");
        JLabel expLabel = new JLabel("experience");
        JLabel expValue = new JLabel("00/00");

        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsValue.setHorizontalAlignment(SwingConstants.CENTER);
        expLabel.setHorizontalAlignment(SwingConstants.CENTER);
        expValue.setHorizontalAlignment(SwingConstants.CENTER);

        expPanel.add(pointsLabel);
        expPanel.add(expLabel);
        expPanel.add(pointsValue);
        expPanel.add(expValue);

        JPanel turnPanel = new JPanel(new GridLayout(4, 1));
        turnPanel.setPreferredSize(new Dimension(300, 300));
        JLabel playerActionLabel = new JLabel("Действие игрока");
        JLabel enemyActionLabel = new JLabel("Действие противника");
        JLabel turnLabel = new JLabel("Чья очередь");
        JLabel stunLabel = new JLabel("Информация о стане");

        playerActionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enemyActionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stunLabel.setHorizontalAlignment(SwingConstants.CENTER);

        turnPanel.add(playerActionLabel);
        turnPanel.add(enemyActionLabel);
        turnPanel.add(turnLabel);
        turnPanel.add(stunLabel);

        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.cyan);
        blankPanel.setPreferredSize(new Dimension(300, 50));

        infoPanel.add(expPanel);
        infoPanel.add(blankPanel);
        infoPanel.add(turnPanel);

        return infoPanel;
    }
//    private void startGame() {
//        JFrame gameFrame = new JFrame();
//        JPanel gamePanel = new JPanel();
//
//        JButton attackButton = new JButton("атака");
//        JButton defenseButton = new JButton("защита");
//        JButton newGameButton = new JButton("новая игра");
//        gamePanel.add(attackButton);
//        gamePanel.add(defenseButton);
//        gamePanel.add(newGameButton);
//        
////        battleEngine.setEnemy();
////        Enemy firstEnemy = battleEngine.getEnemy();
////        Enemy firstEnemy = action.getEnemyes()[0];
////        int[] pattern = behaviorManager.getBehaviorFor(firstEnemy);
//        System.out.println(firstEnemy.getName());
//
//        attackButton.addActionListener((ActionEvent e) -> {
//            attackButtonPressed();
//        });
//        defenseButton.addActionListener((ActionEvent e) -> {
//            defenseButtonPressed();
//        });
//        newGameButton.addActionListener((ActionEvent e) -> {
//            battleEngine.setEnemy();
//            firstEnemy = battleEngine.getEnemy();
//            human.setNewHealth(human.getMaxHealth());
//            System.out.println("");
//            System.out.println("Новая игра! Ваш противник - " + firstEnemy.getName());
//            System.out.println("");
//        });
//        gameFrame.setBounds(150, 50, 400, 300);
//        gameFrame.add(gamePanel);
//        gameFrame.setContentPane(gamePanel);
//        gameFrame.setVisible(true);
////        game.startGame(human);
//    }
//
//    private void attackButtonPressed() {
//        System.out.println("");
//        System.out.println("");
//        battleEngine.processAction(human, firstEnemy, 1);
//        System.out.println("Здоровье противника: " + firstEnemy.getHealth() + " из " + firstEnemy.getMaxHealth());
//        System.out.println("Здоровье игрока: " + human.getHealth() + " из " + human.getMaxHealth());
//        System.out.println("");
//        System.out.println("");
//    }
//
//    private void defenseButtonPressed() {
//        System.out.println("");
//        System.out.println("");
//        battleEngine.processAction(human, firstEnemy, 0);
//        System.out.println("Здоровье противника: " + firstEnemy.getHealth() + " из " + firstEnemy.getMaxHealth());
//        System.out.println("Здоровье игрока: " + human.getHealth() + " из " + human.getMaxHealth());
//        System.out.println("");
//        System.out.println("");
//    }

    public static void main(String[] args) {
        GUIDemo g = new GUIDemo();

    }

    private void openDialog() {

    }
}
