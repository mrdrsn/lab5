package newpackage;

/**
 *
 * @author nsoko
 */
import javax.swing.*;
import java.awt.*;

public class FightFrame extends JFrame {

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
        characterPanel.setLayout(new BorderLayout());

        // Верхняя часть: индикатор здоровья
        JPanel healthPanel = new JPanel();
        healthPanel.setBackground(Color.red);
        healthPanel.setPreferredSize(new Dimension(300, 25));
        healthPanel.setLayout(new BorderLayout());
        JProgressBar healthBar = new JProgressBar(0, 80);
        healthBar.setValue(80); // Пример значения
        healthPanel.add(new JLabel("80/80"), BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        healthPanel.add(new JLabel(damageText), BorderLayout.EAST);
        characterPanel.add(healthPanel, BorderLayout.NORTH);

        // Средняя часть: изображение персонажа
        JPanel imagePanel = new BackgroundPanel("kitana.jpg");
//        imagePanel.setBackground(Color.BLUE);
//        imagePanel.setLayout(new BorderLayout());
//        JLabel characterImage = new JLabel();
//        characterImage.setIcon(new ImageIcon("kitana.jpg")); // Заменить на реальное изображение
//        imagePanel.add(characterImage, BorderLayout.CENTER);
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
//        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
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
//        infoPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        // Очки
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        infoPanel.add(pointsLabel, gbc);
//
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        infoPanel.add(pointsValue, gbc);
//
//        // Опыт
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        infoPanel.add(expLabel, gbc);
//
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
