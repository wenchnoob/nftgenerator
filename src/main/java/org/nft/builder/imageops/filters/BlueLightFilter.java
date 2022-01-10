package org.nft.builder.imageops.filters;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.imageops.ImageOp;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@NoArgsConstructor
@RequiredArgsConstructor
@Component("Blue Light Filter")
public class BlueLightFilter extends ImageOp {

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
