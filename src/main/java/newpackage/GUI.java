/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;

import com.mycompany.lab5.Game;
import com.mycompany.lab5.Player;
import com.mycompany.lab5.Items;
import com.mycompany.lab5.Entity;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 *
 * @author nsoko
 */
public class GUI extends JFrame {

    private Game game = new Game();
    private Player human = null;
    private Entity enemy = null;
    private Items[] items = new Items[3];
    private String nameButton = "";
    private String filePath = "C:\\Users\\nsoko\\OneDrive\\Desktop\\Results.xlsx";

    private JProgressBar playerHealth = new JProgressBar();
    private JProgressBar enemyHealth = new JProgressBar();
    private JLabel someEnemyLabelIdk = new JLabel();
    private JLabel enemyName = new JLabel();
    private JLabel enemyDamageValue = new JLabel();
    private JLabel enemyHealthValue = new JLabel();
    private JLabel playerHealthValue = new JLabel();
    private JLabel pointsValue = new JLabel();
    private JLabel expValue = new JLabel();
    private JLabel playerLevelValue = new JLabel();
    private JLabel enemyLevelValue = new JLabel();
    private JLabel playerDamageValue = new JLabel();
    private JLabel turnLabel = new JLabel();
    private JLabel actionLabel = new JLabel();
    private JRadioButton smallHealOption = new JRadioButton();
    private JRadioButton bigHealOption = new JRadioButton();
    private JRadioButton revivalOption = new JRadioButton();

    public GUI() {
        JPanel startPanel = new JPanel();
        JButton startGameButton = new JButton("Начать новую игру");
        JButton showTableButton = new JButton("Просмотреть таблицу результатов");
        startPanel.add(startGameButton);
        startPanel.add(showTableButton);
        startGameButton.addActionListener((ActionEvent e) -> {
            startGame();
            this.dispose();
        });
        showTableButton.addActionListener((ActionEvent e) -> {
            openDialog();
        });
        setBounds(150, 50, 1280, 720);
        setContentPane(startPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void componentsInitialSettings() {
        playerHealth.setMaximum(80);
        playerHealth.setMinimum(-1);
        
        enemyHealth.setMaximum(80);
        enemyHealth.setMinimum(-1);
        
        pointsValue.setText("00");
        expValue.setText("0/40");
        
        playerLevelValue.setText("0 уровень");
        enemyLevelValue.setText("1 уровень");
        
        playerHealthValue.setText("80/80");
        enemyHealthValue.setText("80/80");
        
        playerDamageValue.setText("16");
        
        //turnLabel изначально пустой
        //actionLabel изначально пустой
        
        smallHealOption.setText("Малое зелье лечение, 0 шт");
        bigHealOption.setText("Большое зелье лечение, 0 шт");
        revivalOption.setText("Крест возрождения, 0 шт");
    }

    private void startGame() {
        JFrame gameFrame = new JFrame();
        JPanel gamePanel = new JPanel();
        
        componentsInitialSettings();
        
        
//        human = game.NewHuman(playerHealth);
//        enemy = game.NewEnemy(someEnemyLabelIdk, enemyName, enemyDamageValue, enemyHealthValue, enemyHealth);
//        game.change.NewRoundTexts(human, enemy, playerHealth, enemyHealth,
//                pointsValue, expValue, playerLevelValue, enemyLevelValue, playerHealthValue, enemyHealthValue, playerDamageValue,
//                turnLabel, actionLabel, game.fight.i, items, smallHealOption, bigHealOption, revivalOption);

        gameFrame.setContentPane(gamePanel);
        gameFrame.setBounds(150, 50, 1280, 720);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    
    private void openDialog() {
    }


}
