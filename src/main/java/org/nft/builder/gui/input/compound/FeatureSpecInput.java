package org.nft.builder.gui.input.compound;

import org.nft.builder.gui.input.InputFactory;
import org.nft.builder.models.Feature;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import org.nft.builder.gui.Window;

public class FeatureSpecInput extends JDialog {

    JLabel selectedLocation = new JLabel();
    JLabel locationLabel = new JLabel("Location Selected: ");

    JLabel name = new JLabel("Name: ");
    JTextField nameIn = new JTextField("");

    JLabel xoffset = new JLabel("X-Offset");
    JTextField xoffsetIn = InputFactory.numericInput("0");

    JLabel yoffset = new JLabel("Y-Offset");
    JTextField yoffsetIn = InputFactory.numericInput("0");

    JLabel zIndex = new JLabel("Z-Index");
    JTextField zIndexIn =InputFactory.numericInput("0");

    JLabel empty = new JLabel(" ");
    JButton submit = new JButton("Submit");

    public FeatureSpecInput(Window win, File srcFile, String title, Dialog.ModalityType modalityType) {
        super(win, title, modalityType);

        setLayout(new GridLayout(0, 2));

        selectedLocation.setText(srcFile.getAbsolutePath());

        selectedLocation.setBorder(new EmptyBorder(10, 10, 10, 10));
        locationLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(locationLabel);
        add(selectedLocation);
        add(name);
        add(nameIn);
        add(xoffset);
        add(xoffsetIn);
        add(yoffset);
        add(yoffsetIn);
        add(zIndex);
        add(zIndexIn);
        add(empty);
        add(submit);

        submit.addActionListener(action ->  {
            win.registerFeature(srcFile, new Feature(srcFile, nameIn.getText(), Integer.parseInt(xoffsetIn.getText()), Integer.parseInt(yoffsetIn.getText()), Integer.parseInt(zIndexIn.getText())));
            dispose();
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
