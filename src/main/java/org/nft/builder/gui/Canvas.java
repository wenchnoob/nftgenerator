package org.nft.builder.gui;

import lombok.Getter;
import org.nft.builder.models.Feature;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Getter
public class Canvas extends JPanel {

    private static BufferedImage img;

    private Set<Feature> features;

    public Canvas() {
        setMinimumSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.BLUE);
        setOpaque(false);
        img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.drawString("Hey what's up", 150, 150);
        features = new HashSet<>();
    }

    public void addFeature(File src, Feature feature) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(src);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (img == null) return;
        img = feature.transform(img);

        addToCanvas(img);
    }

    private void addToCanvas(BufferedImage img) {
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

    public void addFeature(Feature feat) {
        features.add(feat);
        repaint();
    }

    public void removeFeature(Feature feat) {
        features.remove(feat);
        repaint();
    }

    public void addPicture(File file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img == null) return;
        addToCanvas(img);
    }

    public void clear() {
        img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        repaint();
    }

    public void addPicture(String path) {
        addPicture(new File(path));
    }

    public void saveTo(File chosen) {
        if (chosen == null) return;
        try {
            chosen = new File(chosen.getAbsolutePath() + ".png");
            chosen.createNewFile();

            AtomicReference<BufferedImage> img = new AtomicReference<>(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

            features.stream().sorted(Comparator.comparingInt(Feature::getZIndex)).forEach(feature -> {
                int h = Math.max(img.get().getHeight(), feature.getImg().getHeight());
                int w = Math.max(img.get().getWidth(), feature.getImg().getWidth());
                BufferedImage tmp = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) tmp.getGraphics();
                g.drawImage(img.get(), 0, 0, null);
                g.drawImage(feature.getImg(), 0, 0, null);
                img.set(tmp);
            });

            ImageIO.write(img.get(), "PNG", chosen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        features.stream().sorted(Comparator.comparingInt(Feature::getZIndex)).forEach(feature -> {
            g.drawImage(feature.getImg(), 0, 0, null);
        });
    }
}
