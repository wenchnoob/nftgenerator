package org.nft.builder.gui;

import lombok.*;
import org.nft.builder.controllers.FeatureController;
import org.nft.builder.imageops.transformations.Rotations;
import org.nft.builder.models.Feature;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
        features.remove(feat);
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
        List<CustomImage> images = new ArrayList<>();
        permuteAll(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Hashtable<>(), pQueue, images);

        long feat = 0;
        for (CustomImage cimg : images) {
            String path = chosen.getAbsolutePath() + getSaveLocation(chosen.getAbsolutePath(), cimg.getFeatures()) + ++feat;
            File f = new File(path);
            saveTo(f, cimg.getImg());
        }
    }

    private String getSaveLocation(String root, Map<Feature, String> featureStringMap) {
        StringBuilder path = new StringBuilder();
        Queue<Feature> pq = new PriorityQueue<>(Comparator.comparingInt(Feature::getZIndex));
        pq.addAll(featureStringMap.keySet());
        path.append('/');
        Feature cur;
        while (!pq.isEmpty()) {
            cur = pq.poll();
            if (cur.isSortedOn()) path.append(cur.getName() + '/' + featureStringMap.get(cur).split("\\.")[0] + '/');
        }
        String val = path.toString();
        try {
            Files.createDirectories(Path.of(root + val));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return val;
    }

    public void permuteAll(BufferedImage curImage, Map<Feature, String> used, Queue<Feature> remaining, List<CustomImage> images) {
        if (remaining.isEmpty()) {
            images.add(new CustomImage(curImage, new Hashtable<>(used)));
            return;
        }

        Feature cur = remaining.poll();

        FeatureController cont = new FeatureController(cur, this);
        Map<String, BufferedImage> imageMap = cont.allImagesWithNames();
        for (String imgName : imageMap.keySet()) {
            used.put(cur, imgName);
            workOnImage(curImage, imageMap.get(imgName), used, remaining, images);
        }
        remaining.offer(cur);
    }

    private void workOnImage(BufferedImage curImage, BufferedImage img, Map<Feature, String> used, Queue<Feature> remaining, List<CustomImage> images) {
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
        permuteAll(tmp, used, remaining, images);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(draw().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
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

        return img.get();
    }

    @Getter
    @NoArgsConstructor
    @RequiredArgsConstructor
    private class CustomImage {
        @NonNull
        private BufferedImage img;

        // String is the name of the image used from that feature
        @NonNull
        private Map<Feature, String> features;
    }
}
