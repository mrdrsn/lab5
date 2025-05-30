package com.mycompany.lab5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import newpackage.BackgroundPanel;

/**
 *
 * @author nsoko
 */
public class GUIDemo extends JFrame {

    public Player human = new Player();
    public BattleEngine battleEngine = new BattleEngine();
    public GameEngine game = new GameEngine(battleEngine);
    public Enemy firstEnemy = battleEngine.getEnemy();

    private JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton attackButton;
    private JButton defendButton;
    private JButton itemsButton;

    private JLabel playerNameLabel;
    private JLabel enemyNameLabel;
    private BackgroundPanel playerImagePanel;
    private BackgroundPanel enemyImagePanel;

    private JProgressBar playerHealthBar;
    private JProgressBar enemyHealthBar;

    private JLabel playerHealthLabel; //меняется с увеличением уровня
    private JLabel enemyHealthLabel; //меняется с увеличением уровня

    private JLabel playerDamageLabel; //меняется с увеличением уровня
    private JLabel enemyDamageLabel; //меняется с увеличением уровня

    private JLabel playerLevelLabel; //меняется с увеличением уровня
    private JLabel enemyLevelLabel; //меняется с увеличением уровня

    private JPanel turnPanel;
    private JLabel turnLabel;
    private JLabel playerActionLabel;
    private JLabel enemyActionLabel;
    private JLabel counterattackLabel;
    private JLabel stunLabel;

    private boolean isFirstGame = true;

    private BackgroundPanel enemyImage = new BackgroundPanel(firstEnemy.getName() + ".jpg");

    public GUIDemo() {
        JPanel startPanel = new JPanel(new BorderLayout());

        JButton startGameButton = new JButton("Начать новую игру");
        JButton showTableButton = new JButton("Просмотреть таблицу результатов");

        startPanel.add(startGameButton, BorderLayout.NORTH);
        startPanel.add(showTableButton, BorderLayout.SOUTH);

        startGameButton.addActionListener((ActionEvent e) -> {
//            chooseName();
            startGame();
            dispose();
            System.out.println(firstEnemy.getName());
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
                JOptionPane.showMessageDialog(this, "Чтобы начать игру необходимо ввести имя.");
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

        JPanel topPanel = createTopPanel();
        centerPanel = createCenterPanel(firstEnemy, human);
        JPanel bottomPanel = createBottomPanel();

        JButton changeEnemyButton = new JButton("Сменить противника");

        changeEnemyButton.addActionListener((ActionEvent e) -> {
            battleEngine.setEnemy();
            firstEnemy = battleEngine.getEnemy();
            updateEntityPanel();

            System.out.println("");
            System.out.println("Новая игра! Ваш противник - " + firstEnemy.getName());
            System.out.println("");

        });

        attackButton.addActionListener((ActionEvent e) -> {
            manageStunInfo();
            game.hit(human, firstEnemy, 1);
            appendOther(isFirstGame);
            updatePanelsAfterMove();
            updateInfoAfterMove(1, firstEnemy.getAttack(), battleEngine);
//            System.out.println("инфа из кнопки: действие врага " + firstEnemy.getAttack());
        });
        defendButton.addActionListener((ActionEvent e) -> {
            manageStunInfo();
            game.hit(human, firstEnemy, 0);
            appendOther(isFirstGame);
            updatePanelsAfterMove();
            updateInfoAfterMove(0, firstEnemy.getAttack(), battleEngine);
            System.out.println("инфа из кнопки: действие врага " + firstEnemy.getAttack());
        });
        bottomPanel.add(changeEnemyButton);

        gameFrame.add(topPanel, BorderLayout.NORTH);
        gameFrame.add(centerPanel, BorderLayout.CENTER);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);
        gameFrame.setTitle("Лабораторная работа 5");
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setBounds(300, 100, 1280, 720);
        gameFrame.setVisible(true);
    }

    private JPanel createCharacterPanel(Entity entity) {
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout());

        // Верхняя панель: здоровье и урон
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

        // Сохраняем ссылки на компоненты
        if (entity.equals(human)) {
            playerHealthBar = healthBar;
            playerHealthLabel = healthLabel;
            playerDamageLabel = damageLabel;
            playerNameLabel = new JLabel(entity.getName());
            playerLevelLabel = new JLabel(entity.getLevel() + " уровень");
            playerImagePanel = new BackgroundPanel("Китана.jpg");

        } else {
            enemyHealthBar = healthBar;
            enemyHealthLabel = healthLabel;
            enemyDamageLabel = damageLabel;
            enemyNameLabel = new JLabel(entity.getName());
            enemyLevelLabel = new JLabel(entity.getLevel() + " уровень");
            enemyImagePanel = new BackgroundPanel(entity.getName() + ".jpg");
        }

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(Color.YELLOW);
        JLabel nameLabel = entity.equals(human) ? playerNameLabel : enemyNameLabel;
        JLabel levelLabel = entity.equals(human) ? playerLevelLabel : enemyLevelLabel;
        infoPanel.add(nameLabel);
        infoPanel.add(levelLabel);

        JPanel imagePanel = entity.equals(human) ? playerImagePanel : enemyImagePanel;
        characterPanel.add(healthPanel, BorderLayout.NORTH);
        characterPanel.add(imagePanel, BorderLayout.CENTER);
        characterPanel.add(infoPanel, BorderLayout.SOUTH);
        System.out.println(imagePanel.getSize());

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

        turnPanel = new JPanel(new GridLayout(5, 1));
        turnPanel.setPreferredSize(new Dimension(300, 300));
        turnPanel.setBackground(Color.red);
        JLabel playerAction = new JLabel("");
        JLabel enemyAction = new JLabel("");
        JLabel turn = new JLabel("Ход противника");
        JLabel counterattack = new JLabel("");
        JLabel stun = new JLabel("");

        playerActionLabel = playerAction;
        enemyActionLabel = enemyAction;
        turnLabel = turn;
        counterattackLabel = counterattack;
        stunLabel = stun;

        playerAction.setHorizontalAlignment(SwingConstants.CENTER);
        enemyAction.setHorizontalAlignment(SwingConstants.CENTER);
        turn.setHorizontalAlignment(SwingConstants.CENTER);
        counterattack.setHorizontalAlignment(SwingConstants.CENTER);
        stun.setHorizontalAlignment(SwingConstants.CENTER);

        turnPanel.add(turn);
        turnPanel.add(playerAction);
        turnPanel.add(enemyAction);
        turnPanel.add(counterattack);
        turnPanel.add(stun);

        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.cyan);
        blankPanel.setPreferredSize(new Dimension(300, 50));

        infoPanel.add(expPanel);
        infoPanel.add(blankPanel);
        infoPanel.add(turnPanel);

        return infoPanel;
    }

    private void updatePanelsAfterMove() {
        if (human.getHealth() <= 0) {
            playerHealthLabel.setText("0/" + human.getMaxHealth());
            playerHealthBar.setValue(0);
            //победа на стороне противника
            endRound(false);
            repeatLastRound();
        } else {
            playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
            playerHealthBar.setValue(human.getHealth());
        }

        // Обновление здоровья врага
        if (firstEnemy.getHealth() <= 0) {
            enemyHealthLabel.setText("0/" + firstEnemy.getMaxHealth());
            enemyHealthBar.setValue(0);
            //победа на стороне игрока
            endRound(true);
            startNewRound();
        } else {
            enemyHealthLabel.setText(firstEnemy.getHealth() + "/" + firstEnemy.getMaxHealth());
            enemyHealthBar.setValue(firstEnemy.getHealth());
        }

    }

    private void repeatLastRound() {
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        game.startNewRound(human, false);
        turnPanel.removeAll();
//        playerActionLabel.setText("новый текст");
//        JLabel newTextLabel = new JLabel("новый текст");
//        newTextLabel
        turnPanel.add(turnLabel);
        updateInfoToDefault();
        System.out.println(playerActionLabel.getText());
        isFirstGame = false;
        turnPanel.revalidate();
        turnPanel.repaint();
//        turnPanel.add(enemyActionLabel);
//        turnPanel.add(stunLabel);
//        turnPanel.add(counterattackLabel);
    }

    private void appendOther(boolean flag) {
        if (!flag) {
            turnPanel.add(playerActionLabel);
            turnPanel.add(enemyActionLabel);
            turnPanel.add(stunLabel);
            turnPanel.add(counterattackLabel);
        }
    }

    private void updateInfoToDefault() {
        // Сбрасываем все надписи
        playerActionLabel.setText("");
        System.out.println("текст после обновления: " + playerActionLabel.getText());
        enemyActionLabel.setText("");
        stunLabel.setText("");
        counterattackLabel.setText("");
        turnLabel.setText("Ход противника");

        // Обновляем здоровье игрока и врага
        playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
        playerHealthBar.setValue(human.getHealth());

        if (firstEnemy != null) {
            enemyHealthLabel.setText(firstEnemy.getHealth() + "/" + firstEnemy.getMaxHealth());
            enemyHealthBar.setValue(firstEnemy.getHealth());
        }

        // Восстанавливаем кнопки
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        // Перерисовка
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void endRound(boolean hasWon) {
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        itemsButton.setEnabled(false);
        if (hasWon) {
            JOptionPane.showMessageDialog(this, "Вы победили.");
            game.addWin(human);
        } else {
            JOptionPane.showMessageDialog(this, "Вы проиграли.");
        }
    }

    private void startNewRound() {
        //игрок победил => новый враг
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        turnLabel.setText("Ход противника");
        playerActionLabel.setText("");
        enemyActionLabel.setText("");
        stunLabel.setText("");
        counterattackLabel.setText("");
        game.startNewRound(human, true);

        //обновление характеристик игрока
        firstEnemy = battleEngine.getEnemy();
        updateEntityPanel();

        System.out.println("");
        System.out.println("Новая игра! Ваш противник - " + firstEnemy.getName());
        System.out.println("");
    }

    private void updateInfoAfterMove(int playerAction, int enemyAction, BattleEngine battleEngine) {

        if (playerAction == 1 && enemyAction == 1) {
            playerActionLabel.setText("Вы атакуете");
            enemyActionLabel.setText("Враг атакует");
            counterattackLabel.setText("");
        } else if (playerAction == 1 && enemyAction == 0) {
            playerActionLabel.setText("Вы атакуете");
            enemyActionLabel.setText("Враг защищается");
            if (!battleEngine.isEnemyStunned() && !battleEngine.isPlayerStunned()) {
                counterattackLabel.setText("Враг контратаковал Вас");
            }
        } else if (playerAction == 0 && enemyAction == 1) {
            playerActionLabel.setText("Вы защищаетесь");
            enemyActionLabel.setText("Враг атакует");
            if (!battleEngine.isPlayerStunned() && !battleEngine.isEnemyStunned()) {
                counterattackLabel.setText("Вы контратакуете");
            }
        } else if (playerAction == 0 && enemyAction == 0) {
            playerActionLabel.setText("Вы защищаетесь");
            enemyActionLabel.setText("Враг защищается");
            counterattackLabel.setText("");
        }

        if (battleEngine.getPlayerTurn()) {
            turnLabel.setText("Ваш ход");
        } else {
            turnLabel.setText("Ход противника");
        }

    }

    private void updateEntityPanel() {
        enemyImagePanel.setNewImage(firstEnemy.getName() + ".jpg");
        enemyHealthBar.setMaximum(firstEnemy.getMaxHealth());
        enemyHealthBar.setValue(firstEnemy.getMaxHealth());
        enemyHealthLabel.setText(firstEnemy.getHealth() + "/" + firstEnemy.getMaxHealth());
        enemyDamageLabel.setText("Урон " + firstEnemy.getDamage());
        enemyLevelLabel.setText(firstEnemy.getLevel() + " уровень");
        enemyNameLabel.setText(firstEnemy.getName());

        playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
        playerHealthBar.setMaximum(human.getMaxHealth());
        playerHealthBar.setValue(human.getHealth());

        turnPanel.revalidate();
        turnPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIDemo::new);

    }

    private void openDialog() {

    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("FIGHT");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        topPanel.add(titleLabel);
        return topPanel;
    }

    private JPanel createCenterPanel(Entity enemy, Entity player) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(createCharacterPanel(enemy));   // левая панель (враг)
        panel.add(createInfoPanel());             // центральная информация
        panel.add(createCharacterPanel(player));   // правая панель (игрок)
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        itemsButton = new JButton("Предметы");
        attackButton = new JButton("Атаковать");
        defendButton = new JButton("Защититься");

        bottomPanel.add(itemsButton);
        bottomPanel.add(attackButton);
        bottomPanel.add(defendButton);

        return bottomPanel;
    }

    private void manageStunInfo() {
        stunLabel.setText("");
        if (battleEngine.isEnemyStunned()) {
            stunLabel.setText("Враг был оглушен");
        } else if (battleEngine.isPlayerStunned()) {
            stunLabel.setText("Игрок был оглушен");
        }
    }

}
