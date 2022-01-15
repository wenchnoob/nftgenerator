package org.nft.builder.imageops.filters;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.gui.input.InputFactory;
import org.nft.builder.gui.input.filters.IntFilter;
import org.nft.builder.imageops.ImageOp;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@NoArgsConstructor
@RequiredArgsConstructor
@Component("Blue Light Filter")
public class BlueLightFilter extends ImageOp {

    @NonNull
    private int intensity;

    @Override
    public BufferedImage apply(BufferedImage targ) {
        intensity = intensity <= 0 ? 1 : intensity;
        intensity = intensity > 255 ? 255: intensity;

        int w = targ.getWidth();
        int h = targ.getHeight();
        BufferedImage img = new BufferedImage(w, h, targ.getType());

        Color color;
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                color = new Color(targ.getRGB(col, row));
                img.setRGB(col, row, new Color(color.getRed(), color.getGreen(), normalize(color.getBlue() - intensity)).getRGB());
            }
        }

        return img;
    }

    public int normalize(int init) {
        if (init < 0) return 0;
        if (init > 255) return 255;
        return init;
    }

    @Override
    public BufferedImage applyInPlace(BufferedImage targ) {
        return null;
    }

    @Override
    public String[] getUserConfigs(java.awt.Component parent) {
        String[] args = new String[1];
        JTextField intensityIn = InputFactory.numericInput("1");
        intensityIn.setMinimumSize(new Dimension(150, 15));
        intensityIn.setSize(new Dimension(150, 15));
        intensityIn.setPreferredSize(new Dimension(150, 15));
        JButton submit = new JButton("Submit");
        JDialog dialog = new JDialog((Window)parent, "Input Intensity", Dialog.ModalityType.DOCUMENT_MODAL);
        submit.addActionListener(action -> dialog.dispose());
        JPanel container = new JPanel();
        container.add(new JLabel("Intensity"));
        container.add(intensityIn);
        container.add(submit);
        dialog.add(container);
        dialog.pack();
        dialog.setVisible(true);
        String intensity = intensityIn.getText();
        if (intensity == null) args[0] = "255";
        else args[0] = intensity;
        return args;
    }
}
