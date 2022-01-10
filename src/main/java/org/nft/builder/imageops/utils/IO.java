package org.nft.builder.imageops.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IO {

    public static final BufferedImage copy(BufferedImage original) {
        BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        Graphics g = img.getGraphics();
        g.drawImage(original, 0, 0, null);
        return img;
    }

    public static final BufferedImage emptyImage() {
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

}
