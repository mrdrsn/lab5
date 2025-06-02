package com.mycompany.lab5.ui;

import com.mycompany.lab5.battle.BattleEngine;
import com.mycompany.lab5.battle.GameEngine;
import com.mycompany.lab5.items.Items;
import com.mycompany.lab5.items.ItemManager;
import com.mycompany.lab5.model.Entity;
import com.mycompany.lab5.model.Player;
import com.mycompany.lab5.model.Enemy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import com.mycompany.lab5.ui.BackgroundPanel;
import javax.swing.BoxLayout;

/**
 *
 * @author nsoko
 */
public class GUIDemo extends JFrame {

    public Player human = new Player();
    public Enemy enemy = null;
    public GameEngine game;

    private JPanel centerPanel;

    private JButton attackButton = new JButton("Атаковать"); //основные кнопки
    private JButton defendButton = new JButton("Защититься"); //основные кнопки
    private JButton itemsButton = new JButton("Предметы"); //основные кнопки
    private JButton debuffButton = new JButton("Ослабить"); //основные кнопки

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

    private JLabel pointsValue;
    private JLabel expValue;

    private JPanel turnPanel;
    private JLabel turnLabel;
    private JLabel playerActionLabel;
    private JLabel enemyActionLabel;
    private JLabel counterattackLabel;
    private JLabel stunLabel;

    private GUIHelper creator = new GUIHelper(this);

    public GUIDemo() {
        JPanel startPanel = new JPanel(new GridLayout(2, 1));

        JButton startGameButton = new JButton("Начать новую игру");
        JButton showTableButton = new JButton("Просмотреть таблицу результатов");

        startPanel.add(startGameButton);
        startPanel.add(showTableButton);

        startGameButton.addActionListener((ActionEvent e) -> {
            chooseNameAndLocation();
            dispose();
        });

        showTableButton.addActionListener((ActionEvent e) -> {
            openDialog();
        });

        setBounds(150, 50, 400, 300);
        setContentPane(startPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void chooseNameAndLocation() {
        JDialog inputDialog = new JDialog(this, "Введите имя и выберите количество локаций", true);
        inputDialog.setLayout(new GridLayout(2, 1));
        inputDialog.setSize(400, 250);
        inputDialog.setLocationRelativeTo(this);

        JTextField nameField = new JTextField();
        JLabel nameLabel = new JLabel("Введите имя игрока:");

        Integer[] locationOptions = {1, 2, 3};
        JComboBox<Integer> locationComboBox = new JComboBox<>(locationOptions);
        JLabel locationLabel = new JLabel("Выберите количество локаций:");

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setPreferredSize(new Dimension(300, 250));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(locationLabel);
        panel.add(locationComboBox);

        JButton confirmButton = new JButton("Начать игру");
        JButton cancelButton = new JButton("Отмена");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        inputDialog.add(panel);
        inputDialog.add(buttonPanel);

        String[] playerName = new String[1];
        Integer[] selectedLocations = new Integer[1];
        confirmButton.addActionListener((e) -> {
            String enteredName = nameField.getText().trim();
            int selected = (int) locationComboBox.getSelectedItem();
            if (enteredName.isEmpty()) {
                JOptionPane.showMessageDialog(inputDialog, "Имя не может быть пустым!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            game = new GameEngine(selected);
            human.setName(enteredName);
            playerName[0] = enteredName;
            selectedLocations[0] = selected;
            inputDialog.dispose();
            startGame();
        });

        cancelButton.addActionListener((e) -> {
            inputDialog.dispose();
            setVisible(true); // показываем стартовый экран снова
        });
        inputDialog.setVisible(true);
    }

    private void startGame() {
        JFrame gameFrame = new JFrame();
        gameFrame.setLayout(new BorderLayout());
        enemy = game.getFirstEnemy();
        JPanel topPanel = creator.createTopPanel();
        centerPanel = creator.createCenterPanel(enemy, human);
        JPanel bottomPanel = creator.createBottomPanel();

        itemsButton.addActionListener((ActionEvent e) -> {
            openItemsWindow();
        });
        attackButton.addActionListener((ActionEvent e) -> {
            game.hit(human, enemy, 1);
            updatePanelsAfterMove();
            updateInfoAfterMove(1, enemy.getAttack());
        });
        defendButton.addActionListener((ActionEvent e) -> {
            game.hit(human, enemy, 0);
            updatePanelsAfterMove();
            updateInfoAfterMove(0, enemy.getAttack());
        });
        debuffButton.addActionListener((ActionEvent e) -> {
            game.hit(human, enemy, 2); // 2 - действие ослабления
            updatePanelsAfterMove();
            updateInfoAfterMove(2, enemy.getAttack());
            System.out.println("Игрок попытался ослабить врага");
        });

        gameFrame.add(topPanel, BorderLayout.NORTH);
        gameFrame.add(centerPanel, BorderLayout.CENTER);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);
        gameFrame.setTitle("Лабораторная работа 5");
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setBounds(300, 100, 1280, 720);
        gameFrame.setVisible(true);
    }

    private void updatePanelsAfterMove() {
        playerActionLabel.setVisible(true);
        enemyActionLabel.setVisible(true);
        stunLabel.setVisible(true);
        counterattackLabel.setVisible(true);

        updateEntityPanel();
        if (human.getHealth() <= 0) {
            human.setNewHealth(0);
            handleLoss();
        }
        if (enemy.getHealth() <= 0) {
            enemy.setNewHealth(0);
            handleWin();
        }
    }

    private void handleLoss() {
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        itemsButton.setEnabled(false);
        ItemManager itemManager = game.getItemManager();
        Items revivalCross = null;
        for (Items item : itemManager.getItemsList()) {
            if (item.getName().equals("Крест возрождения")) {
                revivalCross = item;
                break;
            }
        }
        if (revivalCross.getCount() != 0) {
            itemManager.useItem(revivalCross, human);
            JOptionPane.showMessageDialog(this, "Вы проиграли, но крест возрождения восстановил вам 5% здоровья!");
            human.healToRevive();
            updateEntityPanel();
            attackButton.setEnabled(true);
            defendButton.setEnabled(true);
            itemsButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Вы проиграли.");
            repeatLastRound();
        }
    }

    private void handleWin() {
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        itemsButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Вы победили.");
        game.addWin(human);
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Игра окончена");
        } else {
            if (game.isNewLevel()) {
                showLevelUpDialog();
            }
            startNewRound();
        }
    }

    private void repeatLastRound() {
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        game.repeatLastRound(human);
        updateEntityPanel();

        playerActionLabel.setVisible(false);
        enemyActionLabel.setVisible(false);
        stunLabel.setVisible(false);
        counterattackLabel.setVisible(false);

        turnPanel.revalidate();
        turnPanel.repaint();
    }

    private void startNewRound() {
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        expValue.setText(human.getExperience() + "/" + human.getNextExperience());
        pointsValue.setText(Integer.toString(human.getPoints()));

        game.startNewRound(human);
        enemy = game.getNextEnemy();
        updateEntityPanel();

        playerActionLabel.setVisible(false);
        enemyActionLabel.setVisible(false);
        stunLabel.setVisible(false);
        counterattackLabel.setVisible(false);

        turnPanel.revalidate();
        turnPanel.repaint();
    }

    private void showLevelUpDialog() {
        JDialog levelUpDialog = new JDialog(this, "Повышение уровня!", true);
        levelUpDialog.setLayout(new BorderLayout());
        levelUpDialog.setSize(450, 200);
        levelUpDialog.setLocationRelativeTo(this);

        JLabel messageLabel = new JLabel("Вы повысили уровень! Выберите бонус:", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton damageButton = new JButton("Увеличить урон на 25%");
        JButton healthButton = new JButton("Увеличить здоровье на 25%");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(damageButton);
        buttonPanel.add(healthButton);

        damageButton.addActionListener((e) -> {
            human.increaseDamageByPercentage(25);
            JOptionPane.showMessageDialog(levelUpDialog, "Ваш урон увеличен на 25%!");
            levelUpDialog.dispose();
            updateEntityPanel();
        });

        healthButton.addActionListener((e) -> {
            human.increaseHealthByPercentage(25);
            JOptionPane.showMessageDialog(levelUpDialog, "Ваше здоровье увеличено на 25%!");
            levelUpDialog.dispose();
            updateEntityPanel();
        });

        levelUpDialog.add(messageLabel, BorderLayout.NORTH);
        levelUpDialog.add(buttonPanel, BorderLayout.CENTER);

        levelUpDialog.setVisible(true);
    }

    private void openItemsWindow() {
        ItemManager itemManager = game.getItemManager();
        List<Items> items = itemManager.getItemsList();

        JDialog itemsDialog = new JDialog(this, "Мешок предметов", true);
        itemsDialog.setLayout(new BorderLayout());
        itemsDialog.setSize(400, 300);
        itemsDialog.setLocationRelativeTo(this);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Items item : items) {
            listModel.addElement(item.getName() + " x" + item.getCount());
        }

        JList<String> itemsList = new JList<>(listModel);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton useButton = new JButton("Использовать");
        JButton closeButton = new JButton("Закрыть");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(useButton);
        buttonPanel.add(closeButton);

        useButton.addActionListener(e -> {
            String selectedItem = itemsList.getSelectedValue();
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(itemsDialog, "Выберите предмет для использования.");
                return;
            }
            String itemName = selectedItem.split(" x")[0];
            Items targetItem = null;
            for (Items item : itemManager.getItemsList()) {
                if (item.getName().equals(itemName)) {
                    targetItem = item;
                    break;
                }
            }
            if (targetItem.getName().equals("Крест возрождения") && human.getHealth() >= 0) {
                JOptionPane.showMessageDialog(itemsDialog, "Вы не можете использовать крест возрождения пока вы живы!!");
                return;
            } else if (targetItem != null && targetItem.getCount() > 0) {
                if (itemManager.useItem(targetItem, human)) {
                    updateEntityPanel();
                    listModel.removeAllElements();
                    for (Items item : itemManager.getItemsList()) {
                        listModel.addElement(item.getName() + " x" + item.getCount());
                    }
                    JOptionPane.showMessageDialog(itemsDialog, "Вы использовали: " + itemName);
                }
            } else {
                JOptionPane.showMessageDialog(itemsDialog, "Вы не можете использовать предмет, которого у вас нет! ");
                return;
            }
        });

        closeButton.addActionListener(e -> itemsDialog.dispose());

        itemsDialog.add(new JScrollPane(itemsList), BorderLayout.CENTER);
        itemsDialog.add(buttonPanel, BorderLayout.SOUTH);
        itemsDialog.setVisible(true);
    }

    private void updateInfoAfterMove(int playerAction, int enemyAction) {
        if (playerAction == 1 && enemyAction == 1) {
            playerActionLabel.setText("Вы атакуете");
            enemyActionLabel.setText("Враг атакует");
            counterattackLabel.setText("");
        } else if (playerAction == 1 && enemyAction == 0) {
            playerActionLabel.setText("Вы атакуете");
            enemyActionLabel.setText("Враг защищается");
            if (!game.isEnemyStunned() && !game.isPlayerStunned()) {
                counterattackLabel.setText("Враг контратаковал Вас");
            }
        } else if (playerAction == 0 && enemyAction == 1) {
            playerActionLabel.setText("Вы защищаетесь");
            enemyActionLabel.setText("Враг атакует");
            if (!game.isPlayerStunned() && !game.isEnemyStunned()) {
                counterattackLabel.setText("Вы контратакуете");
            }
        } else if (playerAction == 0 && enemyAction == 0) {
            playerActionLabel.setText("Вы защищаетесь");
            enemyActionLabel.setText("Враг защищается");
            counterattackLabel.setText("");
        }

        if (game.isPlayerTurn()) {
            turnLabel.setText("Ваш ход");
        } else {
            turnLabel.setText("Ход противника");
        }

        stunLabel.setText("");
        if (game.isEnemyStunned()) {
            stunLabel.setText("Враг был оглушен");
        } else if (game.isPlayerStunned()) {
            stunLabel.setText("Игрок был оглушен");
        }

    }

    private void updateEntityPanel() {
        enemyImagePanel.setNewImage(enemy.getName() + ".jpg");
        enemyDamageLabel.setText("Урон " + enemy.getDamage());
        enemyLevelLabel.setText(enemy.getLevel() + " уровень");
        enemyNameLabel.setText(enemy.getName());

        if (enemy.getHealth() >= 0) {
            enemyHealthLabel.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
            System.out.println("текущее значение здоровья врага " + enemy.getName() + " - " + enemy.getHealth());
            enemyHealthBar.setMaximum(enemy.getMaxHealth());
            enemyHealthBar.setValue(enemy.getHealth());
        } else {
            enemyHealthLabel.setText("0/" + enemy.getMaxHealth());
            enemyHealthBar.setValue(0);
        }
        if (human.getHealth() >= 0) {
            playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
            playerHealthBar.setMaximum(human.getMaxHealth());
            playerHealthBar.setValue(human.getHealth());
        } else {
            playerHealthLabel.setText("0/" + human.getMaxHealth());
            playerHealthBar.setValue(0);
        }

        playerLevelLabel.setText(human.getLevel() + " уровень");
        playerDamageLabel.setText("Урон" + human.getDamage());

        turnPanel.revalidate();
        turnPanel.repaint();
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void openDialog() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIDemo::new);

    }

    public GameEngine getGameEngine() {
        return this.game;
    }

    public JPanel getTurnPanel() {
        return this.turnPanel;
    }

    public JLabel getPointsValueLabel() {
        return this.pointsValue;
    }

    public JLabel getExpValueLabel() {
        return this.expValue;
    }

    public JButton getItemsButton() {
        return this.itemsButton;
    }

    public JButton getAttackButton() {
        return this.attackButton;
    }

    public JButton getDefendButton() {
        return this.defendButton;
    }

    public JButton getDebuffButton() {
        return this.debuffButton;
    }

    public Player getHuman() {
        return this.human;
    }

    public JProgressBar getPlayerHealthBar() {
        return this.playerHealthBar;
    }

    public JLabel getPlayerNameLabel() {
        return this.playerNameLabel;
    }

    public JLabel getPlayerLevelLabel() {
        return this.playerLevelLabel;
    }

    public BackgroundPanel getPlayerImagePanel() {
        return this.playerImagePanel;
    }

    public JLabel getEnemyNameLabel() {
        return this.enemyNameLabel;
    }

    public JLabel getEnemyLevelLabel() {
        return this.enemyLevelLabel;
    }

    public BackgroundPanel getEnemyImagePanel() {
        return this.enemyImagePanel;
    }

    public void setPlayerActionLabel(JLabel playerAction) {
        this.playerActionLabel = playerAction;
    }

    public void setPlayerHealthBar(JProgressBar healthBar) {
        this.playerHealthBar = healthBar;
    }

    public void setPlayerHealthLabel(JLabel healthLabel) {
        this.playerHealthLabel = healthLabel;
    }

    public void setPlayerDamageLabel(JLabel damageLabel) {
        this.playerDamageLabel = damageLabel;
    }

    public void setPlayerNameLabel(JLabel nameLabel) {
        this.playerNameLabel = nameLabel;
    }

    public void setPlayerLevelLabel(JLabel levelLabel) {
        this.playerLevelLabel = levelLabel;
    }

    public void setPlayerImagePanel(BackgroundPanel imagePanel) {
        this.playerImagePanel = imagePanel;
    }

    public void setEnemyActionLabel(JLabel enemyAction) {
        this.enemyActionLabel = enemyAction;
    }

    public void setEnemyHealthBar(JProgressBar healthBar) {
        this.enemyHealthBar = healthBar;
    }

    public void setEnemyHealthLabel(JLabel healthLabel) {
        this.enemyHealthLabel = healthLabel;
    }

    public void setEnemyDamageLabel(JLabel damageLabel) {
        this.enemyDamageLabel = damageLabel;
    }

    public void setEnemyNameLabel(JLabel nameLabel) {
        this.enemyNameLabel = nameLabel;
    }

    public void setEnemyLevelLabel(JLabel levelLabel) {
        this.enemyLevelLabel = levelLabel;
    }

    public void setEnemyImagePanel(BackgroundPanel imagePanel) {
        this.enemyImagePanel = imagePanel;
    }

    public void setPointsValueLabel(JLabel pointsValue) {
        this.pointsValue = pointsValue;
    }

    public void setExpPointsValueLabel(JLabel expValue) {
        this.expValue = expValue;
    }

    public void setTurnPanel(JPanel turnPanel) {
        this.turnPanel = turnPanel;
    }

    public void setTurnLabel(JLabel turnLabel) {
        this.turnLabel = turnLabel;
    }

    public void setCounterattackLabel(JLabel counterattackLabel) {
        this.counterattackLabel = counterattackLabel;
    }

    public void setStunLabel(JLabel stunLabel) {
        this.stunLabel = stunLabel;
    }

}
