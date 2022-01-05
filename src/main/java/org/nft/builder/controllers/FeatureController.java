package org.nft.builder.controllers;

import lombok.Getter;
import lombok.ToString;
import org.nft.builder.gui.Canvas;
import org.nft.builder.models.Feature;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ToString
public class FeatureController {

    private Canvas canvas;

    private File srcFile;

    @Getter
    private Feature feature;

    private ImageCycler imageCycler;

    public FeatureController(String srcPath, Feature feature, Canvas canvas) {
        this(new File(srcPath), feature, canvas);
    }

    public FeatureController(File srcFile, Feature feature, Canvas canvas) {
        this.srcFile = srcFile;
        this.feature = feature;
        this.imageCycler = new ImageCycler(srcFile);
        this.canvas = canvas;
    }

    public FeatureController(Feature feature, Canvas canvas) {
        this.srcFile = feature.getSrcFile();
        this.feature = feature;
        this.imageCycler = new ImageCycler(this.srcFile);
        this.canvas = canvas;
    }

    public boolean changeSource(File srcFile) {
        boolean success = this.imageCycler.changeSource(this.srcFile);
        if (!success) return false;
        this.srcFile = srcFile;
        return true;
    }

    public boolean changeFeature(Feature feature) {
        if (feature == null) throw new IllegalArgumentException("Cannot make feature null.");
        canvas.removeFeature(feature);
        canvas.addFeature(feature);
        this.feature = feature;
        return true;
    }

    public BufferedImage getPrev() {
        try {
            return feature.transform(ImageIO.read(imageCycler.getPrev()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getCur() {
        try {
            return feature.transform(ImageIO.read(imageCycler.getCur()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getNext() {
        try {
            return feature.transform(ImageIO.read(imageCycler.getNext()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getRandom() {
        try {
            return feature.transform(ImageIO.read(imageCycler.getRandom()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JButton getPrevButton() {
        return new JButton("Prev: " + feature.getName()) {
            {
                setMinimumSize(new Dimension(150,  50));
                setMaximumSize(new Dimension(150,  50));
                setPreferredSize(new Dimension(150, 50));
                addActionListener(action -> {
                    getPrev();
                    canvas.repaint();
                });
            }
        };
    }

    public JButton getNextButton() {
        return new JButton("Next: " + feature.getName()) {
            {
                addActionListener(action -> {
                    getNext();
                    canvas.repaint();
                });
            }
        };
    }

    public void setPosition(int amount) {
        imageCycler.setPosition(amount);
    }

    public boolean isAtEnd() {
        return  imageCycler.isAtEnd();
    }

    public boolean isAtStart() {
        return imageCycler.isAtStart();
    }

    public List<BufferedImage> allImages() {
        List<BufferedImage> allImages = new ArrayList<>();
        for (File f: imageCycler.getImages()) {
            try {
                allImages.add(ImageIO.read(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allImages;
    }
}
