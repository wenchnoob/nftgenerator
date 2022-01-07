package org.nft.builder.controllers;

import lombok.*;

import java.io.File;
import java.util.*;
import java.util.List;

@Getter
@Setter
public class ImageCycler {

    private File srcFile;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Random rand = new Random();

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Cycler cycler;

    @Setter(AccessLevel.NONE)
    private List<File> images = new ArrayList<>();

    public ImageCycler(File srcFile) {
        if (!srcFile.isDirectory()) throw new IllegalArgumentException("The root of the ImageCycler must be a directory");

        List<File> images = new ArrayList<>();
        processDirRecursively(srcFile, images);

        this.images = images;
        this.srcFile = srcFile;

        cycler = new Cycler(rand.nextInt(images.size()), 0, this.images.size());
    }

    private List<File> processDir(File dir) {
        if (!dir.isDirectory()) return List.of();
        return List.of(Objects.requireNonNull(dir.listFiles(file -> {
            for (String extension : new String[]{"jpg", "jpeg", "png", "gif"}) {
                if (file.getName().toLowerCase().endsWith(extension))
                    return true;
            }
            return false;
        })));
    }

    private void processDirRecursively(File dir, List<File> collector) {
        if (!dir.isDirectory()) return;
        Arrays.stream(dir.listFiles()).parallel().forEach(file -> {
            if(file.isDirectory()) processDirRecursively(file, collector);
            else
                for (String extension : new String[] { "jpg", "jpeg", "png", "gif" })
                    if (file.getName().toLowerCase().endsWith(extension)) collector.add(file);
        });
    }

    public File getPrev() {
        if (images == null) throw new IllegalStateException("The ImageCycler has not been initialized.");
        return images.get(cycler.prev());
    }

    public File getCur() {
        if (images == null) throw new IllegalStateException("The ImageCycler has not been initialized.");
        return images.get(cycler.getCur());
    }

    public File getNext() {
        if (images == null) throw new IllegalStateException("The ImageCycler has not been initialized.");
        return images.get(cycler.next());
    }

    public File getRandom() {
        if (images == null) throw new IllegalStateException("The ImageCycler has not been initialized.");
        return images.get(cycler.random());
    }

    @Getter
    @AllArgsConstructor
    private class Cycler {

        @Setter(AccessLevel.NONE)
        private int cur;
        @Setter(AccessLevel.NONE)
        private int min;
        @Setter(AccessLevel.NONE)
        private int max;

        public int prev() {
            cur = cur - 1 < min ? max - 1: cur - 1;
            return cur;
        }

        public int next() {
            cur = ++cur % max + min;
            return cur;
        }

        public int random() {
            cur = rand.nextInt(max - min) + min;
            return cur;
        }
    }
}
