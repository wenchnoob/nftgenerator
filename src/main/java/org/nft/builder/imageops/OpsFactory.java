package org.nft.builder.imageops;

import org.nft.builder.imageops.filters.BlueLightFilter;
import org.nft.builder.imageops.transformations.Rotations;

public class OpsFactory {

    public static Filter of(String name) {
        return switch (name) {
            case "BLUE LIGHT" -> blueLight();
            case "Rotations" -> rotation();
            default -> null;
        };
    }

    private static BlueLightFilter blueLight() {
        return null;
    }

    private static Rotations rotation() {
        return null;
    }
}
