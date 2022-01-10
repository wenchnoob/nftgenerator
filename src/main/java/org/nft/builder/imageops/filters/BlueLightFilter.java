package org.nft.builder.imageops.filters;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.imageops.Filter;

import java.awt.image.BufferedImage;

@NoArgsConstructor
@RequiredArgsConstructor
public class BlueLightFilter extends Filter {

    @NonNull
    private int intensity;

    @Override
    public BufferedImage apply(BufferedImage targ) {
        return null;
    }

    @Override
    public BufferedImage applyInPlace(BufferedImage targ) {
        return null;
    }
}
