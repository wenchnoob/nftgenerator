package org.nft.builder.gui;

import lombok.Getter;
import org.nft.builder.controllers.FeatureController;
import org.nft.builder.imageops.transformations.Rotations;
import org.nft.builder.models.Feature;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
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
        features = new HashSet<>();
    }

    public void addFeature(Feature feat) {
        features.add(feat);
        repaint();
    }

    public void removeFeature(Feature feat) {
        System.out.println(features);
        features.remove(feat);
        System.out.println(features);
        repaint();
    }

    public void saveTo(File chosen) {
        saveTo(chosen, draw());
    }

    public void saveTo(File chosen, BufferedImage img) {
        if (chosen == null) return;
        try {
            chosen = new File(chosen.getAbsolutePath() + ".png");
            chosen.createNewFile();
            ImageIO.write(img, "PNG", chosen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shuffleAll(File chosen) {
        Queue<Feature> pQueue = new PriorityQueue<>(Comparator.comparingInt(Feature::getZIndex));
        pQueue.addAll(features);
        if (pQueue.isEmpty()) return;
        List<BufferedImage> images = new ArrayList<>();
        permuteAll(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), pQueue, images);

        long feat = 0;
        for (BufferedImage img : images) saveTo(new File(chosen.getAbsolutePath() + "/" + feat++), img);

    }

    public void permuteAll(BufferedImage curImage, Queue<Feature> remaining, List<BufferedImage> images) {
        if (remaining.isEmpty()) {
            images.add(curImage);
            return;
        }

        Feature cur = remaining.poll();

        FeatureController cont = new FeatureController(cur, this);
        List<BufferedImage> imageList = cont.allImages();
        for (BufferedImage img : imageList) {
            workOnImage(curImage, img, remaining, images);
        }
        remaining.offer(cur);
    }

    private void workOnImage(BufferedImage curImage, BufferedImage img, Queue<Feature> remaining, List<BufferedImage> images) {
        int w;
        int h;
        BufferedImage tmp;
        Graphics g;
        w = Math.max(curImage.getWidth(), img.getWidth());
        h = Math.max(curImage.getHeight(), img.getHeight());
        tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        g = tmp.getGraphics();
        g.drawImage(curImage, 0, 0, null);
        g.drawImage(img, 0, 0, null);
        permuteAll(tmp, remaining, images);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(draw(), 0, 0, null);
    }

    private BufferedImage draw() {
        AtomicReference<BufferedImage> img = new AtomicReference<>(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

        features.stream().sorted(Comparator.comparingInt(Feature::getZIndex)).forEach(feature -> {
            if (feature.getImg() == null) new FeatureController(feature, this).getCur();
            int h = Math.max(img.get().getHeight(), feature.getImg().getHeight());
            int w = Math.max(img.get().getWidth(), feature.getImg().getWidth());
            BufferedImage tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) tmp.getGraphics();
            g.drawImage(img.get(), 0, 0, null);
            g.drawImage(feature.getImg(), 0, 0, null);
            img.set(tmp);
        });

        return new Rotations().rotateRight90(img.get());
    }
}
