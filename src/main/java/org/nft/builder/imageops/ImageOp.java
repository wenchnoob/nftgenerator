package org.nft.builder.imageops;

import lombok.*;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public abstract class ImageOp {

    @NonNull
    private String name;

    public abstract BufferedImage apply(BufferedImage targ);
    public abstract BufferedImage applyInPlace(BufferedImage targ);

    public abstract String[] getUserConfigs(Component component);
}
