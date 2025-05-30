package newpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author nsoko
 */

public class FightFrame extends JFrame {
    
    public JButton itemsButton = new JButton("Предметы");
    public JButton attackButton = new JButton("Атака");
    public JButton defendButton = new JButton("Защита");
    
    

    public FightFrame() {
        setTitle("Fight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 100, 900, 600);
        setLayout(new BorderLayout());

        // Верхняя панель с заголовком
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("FIGHT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Центральная панель
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        // Левая панель (противник)
        JPanel leftPanel = createCharacterPanel("Kitana", 16, "n level"); //заменить имя на enemy
        leftPanel.setBackground(Color.ORANGE);
        // Центральная панель (информация)
        JPanel middlePanel = createInfoPanel();
        middlePanel.setBackground(Color.CYAN);

        // Правая панель (Kitana)
        JPanel rightPanel = createCharacterPanel("Kitana", 14, "n level");
        rightPanel.setBackground(Color.RED);

        // Добавление панелей в центральную панель
        centerPanel.add(leftPanel);
        centerPanel.add(middlePanel);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Нижняя панель с кнопками
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton itemsButton = new JButton("Предметы");
        JButton attackButton = new JButton("Атаковать");
        JButton defendButton = new JButton("Защититься");
        bottomPanel.add(itemsButton);
        bottomPanel.add(attackButton);
        bottomPanel.add(defendButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Отображение окна
        setVisible(true);
    }

    // Метод для создания панели с информацией о персонаже
    private JPanel createCharacterPanel(String name, int damage, String levelText) {
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout());

        // Верхняя часть: индикатор здоровья
        JPanel healthPanel = new JPanel();
        
        healthPanel.setBackground(Color.red);
        healthPanel.setPreferredSize(new Dimension(300, 45));
        healthPanel.setLayout(new BorderLayout());
        
        JProgressBar healthBar = new JProgressBar(0, 80);
        healthBar.setForeground(Color.green);
        healthBar.setValue(80); // Пример значения
        
        JLabel healthLabel = new JLabel("80/80");
        String damageText = "Урон " + Integer.toString(damage);
        healthPanel.add(healthLabel, BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        healthPanel.add(new JLabel(damageText), BorderLayout.EAST);
        characterPanel.add(healthPanel, BorderLayout.NORTH);

        // Средняя часть: изображение персонажа
        String imagePath = name + ".jpg";
        JPanel imagePanel = new BackgroundPanel(imagePath);
        characterPanel.add(imagePanel, BorderLayout.CENTER);

        // Нижняя часть: имя и уровень
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.YELLOW);
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoPanel.add(new JLabel(name));
        infoPanel.add(new JLabel(levelText));
        characterPanel.add(infoPanel, BorderLayout.SOUTH);

        return characterPanel;
    }

    // Метод для создания центральной панели с информацией
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
        blankPanel.setPreferredSize(new Dimension(300,50));
        
        infoPanel.add(expPanel);
        infoPanel.add(blankPanel);
        infoPanel.add(turnPanel);

        return infoPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FightFrame());
    }
}
