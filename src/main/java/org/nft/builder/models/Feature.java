package org.nft.builder.models;


import lombok.*;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Feature {

    @NonNull
    private String name;

    @NonNull
    private int xOffset, yOffset;

    @NonNull
    private int zIndex;
    private int type;

    @Setter(AccessLevel.NONE)
    private BufferedImage img;

    public BufferedImage transform(BufferedImage img) {
        if (img == null) throw new IllegalArgumentException("Image to transform cannot be null");

        int w = img.getWidth();
        int h = img.getHeight();
        this.img = new BufferedImage(w + xOffset, h + yOffset, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) this.img.getGraphics();
        g.drawImage(img, xOffset, yOffset, null);
        g.dispose();
        return this.img;
    }
}
