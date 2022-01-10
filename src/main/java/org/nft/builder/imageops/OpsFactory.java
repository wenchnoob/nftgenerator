package org.nft.builder.imageops;

import org.nft.builder.imageops.filters.BlueLightFilter;
import org.nft.builder.imageops.transformations.Rotations;

public class OpsFactory {

    public static ImageOp of(String name, String...args) {
        return switch (name) {
            case "BLUE LIGHT" -> blueLight(args);
            case "Rotation" -> rotation(args);
            default -> null;
        };
    }

    private static BlueLightFilter blueLight(String...args) {
        return null;
    }

    private static Rotations rotation(String...args) {
        return new Rotations(args[0]);
    }
}
