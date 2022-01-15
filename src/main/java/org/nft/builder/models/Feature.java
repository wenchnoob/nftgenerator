package org.nft.builder.models;


import lombok.*;
import org.nft.builder.imageops.ImageOp;
import org.nft.builder.imageops.utils.IO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Feature {

    @NonNull
    private File srcFile;

    @NonNull
    private String name;

    @NonNull
    private int xOffset, yOffset;

    @NonNull
    private int zIndex;

    private int type;

    @NonNull
    private List<ImageOp> imageOps;

    private boolean isDirty;

    @NonNull
    private BufferedImage originalImage;

    private BufferedImage img;

    private boolean sortOn = false;

    public BufferedImage getImg() {
        if (originalImage == null) throw new IllegalArgumentException("Original Image not set for this feature.");
        if (!isDirty) return img;

        AtomicReference<BufferedImage> filteredImage = new AtomicReference<>(IO.copy(originalImage));

        imageOps.stream().parallel().forEach(filter -> {
            filteredImage.set(filter.apply(filteredImage.get()));
        });

        int w = filteredImage.get().getWidth();
        int h = filteredImage.get().getHeight();
        this.img = new BufferedImage(w + xOffset, h + yOffset, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) this.img.getGraphics();
        g.drawImage(filteredImage.get(), xOffset, yOffset, null);
        g.dispose();
        return this.img;
    }

    public void setImage(BufferedImage img) {
        this.originalImage = img;
        this.isDirty = true;
    }

    public void addFilter(ImageOp imageOp) {
        imageOps.add(imageOp);
        isDirty = true;
    }

    public void removeFilter(ImageOp filter) {
        imageOps.remove(filter);
        isDirty = true;
    }

    public File getSrcFile() {
        return this.srcFile;
    }

    public void setSrcFile(File srcFile) {
        this.srcFile = srcFile;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
        this.isDirty = true;
    }

    public int getYOffset() {
        return this.yOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
        this.isDirty = true;
    }

    public int getZIndex() {
        return this.zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
        this.isDirty = true;
    }

    public boolean isSortedOn() {
        return this.sortOn;
    }

    public void sortOn(boolean sortOn) {
        this.sortOn = sortOn;
    }
}
