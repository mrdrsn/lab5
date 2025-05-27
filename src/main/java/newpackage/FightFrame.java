/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;

/**
 *
 * @author nsoko
 */
import javax.swing.*;
import java.awt.*;

public class FightFrame extends JFrame {

    public FightFrame() {
        // Настройка основного окна
        setTitle("Fight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
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
//        centerPanel.setBackground(Color.ORANGE);
        centerPanel.setLayout(new GridLayout(1, 3));

        // Левая панель (противник)
        JPanel leftPanel = createCharacterPanel("Enemy (type)", "Damage n", "n level");
        leftPanel.setBackground(Color.ORANGE);
        // Центральная панель (информация)
        JPanel middlePanel = createInfoPanel();
        middlePanel.setBackground(Color.CYAN);

        // Правая панель (Kitana)
        JPanel rightPanel = createCharacterPanel("Kitana", "Damage n", "n level");
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
    private JPanel createCharacterPanel(String name, String damageText, String levelText) {
        JPanel characterPanel = new JPanel();
//        characterPanel.setBackground(Color.red);
        characterPanel.setLayout(new BorderLayout());

        // Верхняя часть: индикатор здоровья
        JPanel healthPanel = new JPanel();
        healthPanel.setBackground(Color.red);
        healthPanel.setPreferredSize(new Dimension(300,25));
        healthPanel.setLayout(new BorderLayout());
        JProgressBar healthBar = new JProgressBar(0, 80);
        healthBar.setValue(80); // Пример значения
        healthPanel.add(new JLabel("80/80"), BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        healthPanel.add(new JLabel(damageText), BorderLayout.EAST);
        characterPanel.add(healthPanel, BorderLayout.NORTH);

        // Средняя часть: изображение персонажа
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.BLUE);
        imagePanel.setLayout(new BorderLayout());
        JLabel characterImage = new JLabel();
        characterImage.setIcon(new ImageIcon("character_image.png")); // Заменить на реальное изображение
        imagePanel.add(characterImage, BorderLayout.CENTER);
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
        
//        infoPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        // Очки
//        JLabel pointsLabel = new JLabel("points");
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        infoPanel.add(pointsLabel, gbc);
//
//        JLabel pointsValue = new JLabel("00");
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        infoPanel.add(pointsValue, gbc);
//
//        // Опыт
//        JLabel expLabel = new JLabel("experience");
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        infoPanel.add(expLabel, gbc);
//
//        JLabel expValue = new JLabel("00/00");
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        infoPanel.add(expValue, gbc);
//
//        // Результат удара
//        JLabel hitResultLabel = new JLabel("result of hit");
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        infoPanel.add(hitResultLabel, gbc);
//
//        JLabel hitResultValue = new JLabel("");
//        gbc.gridx = 1;
//        gbc.gridy = 2;
//        infoPanel.add(hitResultValue, gbc);
//
//        // Ход
//        JLabel turnLabel = new JLabel("turn");
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        infoPanel.add(turnLabel, gbc);
//
//        JLabel turnValue = new JLabel("stun");
//        gbc.gridx = 1;
//        gbc.gridy = 3;
//        infoPanel.add(turnValue, gbc);

        return infoPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FightFrame());
    }
}