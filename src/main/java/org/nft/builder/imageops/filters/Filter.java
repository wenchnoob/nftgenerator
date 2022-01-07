package org.nft.builder.imageops.filters;

import java.awt.image.BufferedImage;

public interface Filter {
    BufferedImage apply(BufferedImage targ);
    BufferedImage applyInPlace(BufferedImage targ);
}
