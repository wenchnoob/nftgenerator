package org.nft.builder.controllers;

import lombok.AccessLevel;
import lombok.Getter;
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

@Getter
public class FeatureController {

    private Canvas canvas;

    private File srcFile;

    private Feature feature;

    @Getter(AccessLevel.NONE)
    private ImageCycler imageCycler;

    private JButton prevButton;

    private JButton nextButton;

    public FeatureController(Feature feature, Canvas canvas) {
        this.srcFile = feature.getSrcFile();
        this.feature = feature;
        this.imageCycler = new ImageCycler(this.srcFile);
        this.canvas = canvas;

        prevButton = new JButton("Prev: " + feature.getName()) {
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

        nextButton = new JButton("Next: " + feature.getName()) {
            {
                addActionListener(action -> {
                    getNext();
                    canvas.repaint();
                });
            }
        };
    }

    public BufferedImage getPrev() {
        try {
            feature.setImage(ImageIO.read(imageCycler.getPrev()));
            return feature.getImg();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getCur() {
        try {
            feature.setImage(ImageIO.read(imageCycler.getCur()));
            return feature.getImg();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getNext() {
        try {
            feature.setImage(ImageIO.read(imageCycler.getNext()));
            return feature.getImg();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getRandom() {
        try {
            feature.setImage(ImageIO.read(imageCycler.getRandom()));
            return feature.getImg();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<BufferedImage> allImages() {
        List<BufferedImage> allImages = new ArrayList<>();
        for (File f: imageCycler.getImages()) {
            try {
                feature.setImage(ImageIO.read(f));
                allImages.add(feature.getImg());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allImages;
    }
}
