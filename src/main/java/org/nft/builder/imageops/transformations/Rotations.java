package org.nft.builder.imageops.transformations;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.imageops.ImageOp;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@Component("Rotation")
@NoArgsConstructor
@RequiredArgsConstructor
public class Rotations extends ImageOp {

    @NonNull
    String rotation;

    private UnaryOperator<BufferedImage> rotateRight90 = targ -> {
        int height = targ.getWidth();
        int width = targ.getHeight();
        BufferedImage res = new BufferedImage(width, height, targ.getType());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) res.setRGB(j, i, targ.getRGB(i, j));
        }
        return res;
    };

    private Consumer<BufferedImage> rotateRight90InPlace = targ -> {
        int height = targ.getWidth();
        int width = targ.getHeight();
        if (height != width) throw new IllegalArgumentException("Image must be square for in place roation");
        BufferedImage res = new BufferedImage(width, height, targ.getType());
        for (int i = 0; i < height; i++) {
            for (int j = i; j < width; j++) targ.setRGB(j, i, targ.getRGB(i, j));
        }
    };

    @Override
    public BufferedImage apply(BufferedImage targ) {
        return switch (rotation) {
            case "90" -> rotateRight90.apply(targ);
            case "180" -> rotateRight90.andThen(rotateRight90).apply(targ);
            case "270" -> rotateRight90.andThen(rotateRight90).andThen(rotateRight90).apply(targ);
            default -> targ;
        };
    }

    @Override
    public BufferedImage applyInPlace(BufferedImage targ) {
        switch (rotation) {
            case "90" -> rotateRight90InPlace.accept(targ);
            case "180" -> rotateRight90InPlace.andThen(rotateRight90InPlace).accept(targ);
            case "270" -> rotateRight90InPlace.andThen(rotateRight90InPlace).andThen(rotateRight90InPlace).accept(targ);
        };
        return targ;
    }
}
