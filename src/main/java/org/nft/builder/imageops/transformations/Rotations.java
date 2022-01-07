package org.nft.builder.imageops.transformations;

import java.awt.image.BufferedImage;

public class Rotations {

    public BufferedImage rotateRight90(BufferedImage targ) {
        int height = targ.getWidth();
        int width = targ.getHeight();
        BufferedImage res = new BufferedImage(width, height, targ.getType());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) res.setRGB(j, i, targ.getRGB(i, j));
        }
        return res;
    }

    public void rotateRight90InPlace(BufferedImage targ) {
        int height = targ.getWidth();
        int width = targ.getHeight();
        if (height != width) throw new IllegalArgumentException("Image must be square for in place roation");
        BufferedImage res = new BufferedImage(width, height, targ.getType());
        for (int i = 0; i < height; i++) {
            for (int j = i; j < width; j++) targ.setRGB(j, i, targ.getRGB(i, j));
        }
    }
}
