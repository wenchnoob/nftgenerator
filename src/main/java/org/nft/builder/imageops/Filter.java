package org.nft.builder.imageops;

import lombok.*;

import java.awt.image.BufferedImage;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public abstract class Filter {

    @NonNull
    private String name;

    public abstract BufferedImage apply(BufferedImage targ);
    public abstract BufferedImage applyInPlace(BufferedImage targ);
}
