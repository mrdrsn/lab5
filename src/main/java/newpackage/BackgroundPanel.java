package newpackage;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
//    private String imagePath;
    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(imagePath)).getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}