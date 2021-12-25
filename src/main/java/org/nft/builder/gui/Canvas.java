package org.nft.builder.gui;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class Canvas extends JPanel {

    private static BufferedImage img;

    public Canvas() {
        setMinimumSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.BLUE);
        setOpaque(false);
        img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.drawString("Hey what's up", 150, 150);
    }

    public void addPicture(File file) {
        addPicture(file.getAbsolutePath());
    }

    public void clear() {
        img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        repaint();
    }

    public void addPicture(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w = Math.max(img.getWidth(), this.img.getWidth());
        int h = Math.max(img.getHeight(), this.img.getHeight());
        BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) temp.getGraphics();
        g2d.drawImage(this.img, 0, 0, this);
        g2d.drawImage(img, 0, 0, this);
        this.img = temp;
        g2d.dispose();
        repaint();
    }

    public void saveTo(File chosen) {
        if (chosen == null) return;
        try {
            chosen = new File(chosen.getAbsolutePath()+".png");
            chosen.createNewFile();
            ImageIO.write(this.img, "PNG", chosen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.img, 0, 0, this);
    }
}
