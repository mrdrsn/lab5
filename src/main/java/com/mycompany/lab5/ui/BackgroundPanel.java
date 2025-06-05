package com.mycompany.lab5.ui;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class BackgroundPanel extends JPanel {
    
    private Image backgroundImage;

    public BackgroundPanel(){
        
    }
    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(imagePath)).getImage();
    }
    public void setNewImage(String newPath){
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(newPath)).getImage();
        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}