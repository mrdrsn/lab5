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

/**
 *
 * @author nsoko
 */
public class GUIDemo extends JFrame {

    public Player human = new Player();
    public BattleEngine battleEngine = new BattleEngine();
    public GameEngine game = new GameEngine(battleEngine);
    public Enemy enemy = null;

    private JPanel centerPanel;
    private JButton attackButton;
    private JButton defendButton;
    private JButton itemsButton;
    private JButton debuffButton;

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

    private boolean isFirstGame = true;

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
            game.setLocationNumber(selected);
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
        enemy = game.getEnemy(0);
        JPanel topPanel = createTopPanel();
        centerPanel = createCenterPanel(enemy, human);
        JPanel bottomPanel = createBottomPanel();

        attackButton.addActionListener((ActionEvent e) -> {
            manageStunInfo();
            game.hit(human, enemy, 1);
            appendOther(isFirstGame);
            updatePanelsAfterMove();
            updateInfoAfterMove(1, enemy.getAttack(), battleEngine);
        });
        defendButton.addActionListener((ActionEvent e) -> {
            manageStunInfo();
            game.hit(human, enemy, 0);
            appendOther(isFirstGame);
            updatePanelsAfterMove();
            updateInfoAfterMove(0, enemy.getAttack(), battleEngine);
        });
        itemsButton.addActionListener((ActionEvent e) -> {
            openItemsWindow();
        });
        debuffButton.addActionListener((ActionEvent e) -> {
            manageStunInfo();
            game.hit(human, enemy, 2); // 2 - действие ослабления
            updatePanelsAfterMove();
            updateInfoAfterMove(2, enemy.getAttack(), battleEngine);
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

    private JPanel createCharacterPanel(Entity entity) {
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout());

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
        pointsValue = new JLabel("00");
        JLabel expLabel = new JLabel("experience");
        expValue = new JLabel("00/40");

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
//            repeatLastRound();
        } else {
            playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
            playerHealthBar.setValue(human.getHealth());
        }

        if (enemy.getHealth() <= 0) {
            enemyHealthLabel.setText("0/" + enemy.getMaxHealth());
            enemyHealthBar.setValue(0);
            //победа на стороне игрока
            endRound(true);
            if (!game.getGameOver()) {
                startNewRound();
            }
        } else {
            enemyHealthLabel.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
            enemyHealthBar.setValue(enemy.getHealth());
        }

    }

    private void endRound(boolean hasWon) {
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        itemsButton.setEnabled(false);
        if (hasWon) {
            JOptionPane.showMessageDialog(this, "Вы победили.");
            game.addWin(human);
            if (game.getGameOver()) {
                JOptionPane.showMessageDialog(this, "Игра окончена");
            }
        } else {
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
                JOptionPane.showMessageDialog(this, "Вы были мертвы, но крест возрождения восстановил вам 5% здоровья!");
                human.healToRevive();
                attackButton.setEnabled(true);
                defendButton.setEnabled(true);
                itemsButton.setEnabled(true);
                updateEntityPanel();
            } else {
                repeatLastRound(); // Возобновляем раунд
                JOptionPane.showMessageDialog(this, "Вы проиграли.");
            }
        }
        if (game.isNewLevel()) {
            showLevelUpDialog();
        }
    }

    private void repeatLastRound() {
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        game.startNewRound(human, false);

        updateEntityPanel();

        turnPanel.removeAll();
        turnPanel.add(turnLabel);
        System.out.println(playerActionLabel.getText());
        isFirstGame = false;
        turnPanel.revalidate();
        turnPanel.repaint();
    }

    private void startNewRound() {
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        itemsButton.setEnabled(true);

        expValue.setText(human.getExperience() + "/" + human.getNextExperience());
        pointsValue.setText(Integer.toString(human.getPoints()));
        game.startNewRound(human, true);

        enemy = battleEngine.getEnemy();
        playerLevelLabel.setText(human.getLevel() + " уровень");
        enemyLevelLabel.setText(enemy.getLevel() + " уровень");

        playerDamageLabel.setText("Урон" + human.getDamage());
        enemyDamageLabel.setText("Урон" + enemy.getDamage());

        updateEntityPanel();

        turnPanel.removeAll();
        turnPanel.add(turnLabel);
        System.out.println(playerActionLabel.getText());
        isFirstGame = false;
        turnPanel.revalidate();
        turnPanel.repaint();
    }

    private void showLevelUpDialog() {
        // Создаём модальное диалоговое окно
        JDialog levelUpDialog = new JDialog(this, "Повышение уровня!", true);
        levelUpDialog.setLayout(new BorderLayout());
        levelUpDialog.setSize(450, 200);
        levelUpDialog.setLocationRelativeTo(this);

        // Сообщение
        JLabel messageLabel = new JLabel("Вы повысили уровень! Выберите бонус:", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Кнопки для выбора
        JButton damageButton = new JButton("Увеличить урон на 25%");
        JButton healthButton = new JButton("Увеличить здоровье на 25%");

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(damageButton);
        buttonPanel.add(healthButton);

        // Обработчики нажатий
        damageButton.addActionListener((e) -> {
            human.increaseDamageByPercentage(25); // Предполагается, что такой метод есть
            JOptionPane.showMessageDialog(levelUpDialog, "Ваш урон увеличен на 25%!");
            levelUpDialog.dispose();
            updateEntityPanel(); // Обновляем отображение характеристик игрока
        });

        healthButton.addActionListener((e) -> {
            human.increaseHealthByPercentage(25); // Предполагается, что такой метод есть
            JOptionPane.showMessageDialog(levelUpDialog, "Ваше здоровье увеличено на 25%!");
            levelUpDialog.dispose();
            updateEntityPanel(); // Обновляем отображение характеристик игрока
        });

        // Добавляем компоненты
        levelUpDialog.add(messageLabel, BorderLayout.NORTH);
        levelUpDialog.add(buttonPanel, BorderLayout.CENTER);

        // Отображаем окно
        levelUpDialog.setVisible(true);
    }

    private void openItemsWindow() {
        ItemManager itemManager = game.getItemManager();
        List<Items> items = itemManager.getItemsList();

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "У вас нет предметов.", "Инвентарь пуст", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Создаём диалоговое окно
        JDialog itemsDialog = new JDialog(this, "Мешок предметов", true);
        itemsDialog.setLayout(new BorderLayout());
        itemsDialog.setSize(400, 300);
        itemsDialog.setLocationRelativeTo(this);

        // Список предметов
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Items item : items) {
            listModel.addElement(item.getName() + " x" + item.getCount());
        }

        JList<String> itemsList = new JList<>(listModel);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Панель с кнопками
        JButton useButton = new JButton("Использовать");
        JButton closeButton = new JButton("Закрыть");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(useButton);
        buttonPanel.add(closeButton);

        // Действие при нажатии "Использовать"
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

            if (targetItem != null && targetItem.getCount() > 0) {
                if (itemManager.useItem(targetItem, human)) {
                    updateEntityPanel(); // Обновляем здоровье в GUI

                    // Обновляем список
                    listModel.removeAllElements();
                    for (Items item : itemManager.getItemsList()) {
                        listModel.addElement(item.getName() + " x" + item.getCount());
                    }

                    JOptionPane.showMessageDialog(itemsDialog, "Вы использовали: " + itemName);
                }
            }
        });

        // Закрытие окна
        closeButton.addActionListener(e -> itemsDialog.dispose());

        // Добавляем компоненты
        itemsDialog.add(new JScrollPane(itemsList), BorderLayout.CENTER);
        itemsDialog.add(buttonPanel, BorderLayout.SOUTH);
        itemsDialog.setVisible(true);
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
        enemyImagePanel.setNewImage(enemy.getName() + ".jpg");
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getMaxHealth());
        enemyHealthLabel.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
        enemyDamageLabel.setText("Урон " + enemy.getDamage());
        enemyLevelLabel.setText(enemy.getLevel() + " уровень");
        enemyNameLabel.setText(enemy.getName());
        if (human.getHealth() >= 0) {
            playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
        } else {
            playerHealthLabel.setText("0/" + human.getMaxHealth());
        }
        playerHealthBar.setMaximum(human.getMaxHealth());
        playerHealthBar.setValue(human.getHealth());

        turnPanel.revalidate();
        turnPanel.repaint();
        centerPanel.revalidate();
        centerPanel.repaint();
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
        debuffButton = new JButton("Ослабить");

        bottomPanel.add(itemsButton);
        bottomPanel.add(attackButton);
        bottomPanel.add(defendButton);
        bottomPanel.add(debuffButton);

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

    private void appendOther(boolean flag) {
        if (!flag) {
            turnPanel.add(playerActionLabel);
            turnPanel.add(enemyActionLabel);
            turnPanel.add(stunLabel);
            turnPanel.add(counterattackLabel);
            turnPanel.repaint();
            turnPanel.revalidate();
        }
    }
}
