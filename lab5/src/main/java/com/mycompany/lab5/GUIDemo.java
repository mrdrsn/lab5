package com.mycompany.lab5;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author nsoko
 */
public class GUIDemo extends JFrame {

    public GameEngine game = new GameEngine();
    public Player human = new Player(0, 80, 16, 1);
    public CharacterAction action = new CharacterAction();
    public BattleEngine battleEngine = new BattleEngine();
    public EnemyBehaviorManager behaviorManager = new EnemyBehaviorManager();

    public GUIDemo() {
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

    private void startGame() {

        JFrame gameFrame = new JFrame();
        gameFrame.setBounds(150, 50, 400, 300);
        JPanel gamePanel = new JPanel();
        gameFrame.add(gamePanel);

        JButton attackButton = new JButton("атака");
        JButton defenseButton = new JButton("защита");

        gamePanel.add(attackButton);
        gamePanel.add(defenseButton);

        Enemy firstEnemy = action.getEnemyes()[0];
        attackButton.addActionListener((ActionEvent e) -> {
            System.out.println(firstEnemy.getName());
//            int[] pattern = behaviorManager.getBehaviorFor(firstEnemy);
//            for(int p: pattern){
//                System.out.print(p + " ");
//            }
            System.out.println("");
            System.out.println("");
            battleEngine.processAction(human, firstEnemy, 1);
            System.out.println("Здоровье противника: " + firstEnemy.getHealth() + " из " + firstEnemy.getMaxHealth());
            System.out.println("Здоровье игрока: " + human.getHealth() + " из " + human.getMaxHealth());
            System.out.println("");
            System.out.println("");
        });
        defenseButton.addActionListener((ActionEvent e) -> {
            System.out.println("");
            System.out.println("");
            battleEngine.processAction(human, firstEnemy, 0);
            System.out.println("Здоровье противника: " + firstEnemy.getHealth() + " из " + firstEnemy.getMaxHealth());
            System.out.println("Здоровье игрока: " + human.getHealth() + " из " + human.getMaxHealth());
            System.out.println("");
            System.out.println("");
        });
        gameFrame.setContentPane(gamePanel);
        gameFrame.setVisible(true);
//        game.startGame(human);

    }

    public static void main(String[] args) {
        GUIDemo g = new GUIDemo();
    }

    private void openDialog() {

    }
}
