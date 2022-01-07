package org.nft.builder.gui.input.compound;

import org.nft.builder.controllers.FeaturesManager;
import org.nft.builder.gui.input.InputFactory;
import org.nft.builder.imageops.filters.BlueLightFilter;
import org.nft.builder.imageops.filters.Filter;
import org.nft.builder.models.Feature;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import org.nft.builder.gui.Window;
import org.nft.builder.gui.Menu;

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

    JButton addFilters = new JButton("Add Filters");
    JLabel filtersLabel = new JLabel("Filters: ");

    List<Filter> filters = new ArrayList<>();

    public FeatureSpecInput(Window win, Menu menu, FeaturesManager featuresManager, File srcFile, String title, Dialog.ModalityType modalityType) {
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
        add(addFilters);
        add(new JLabel(""));
        add(filtersLabel);
        add(new JLabel(""));
        add(submit);

        addFilters.addActionListener(action -> {
            Object in = JOptionPane.showInputDialog(this, "Select a Filter", "Filter Selection", JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Hey", "Bye", "Yes", "No"}, "Hey");
            addFilter(new BlueLightFilter());
            //System.out.println(in);
        });

        submit.addActionListener(action ->  {
            String name = nameIn.getText();
            int xOffset = Integer.parseInt(xoffsetIn.getText());
            int yOffset = Integer.parseInt(yoffsetIn.getText());
            int zIndex = Integer.parseInt(zIndexIn.getText());
            Feature feat = new Feature(srcFile, name, xOffset, yOffset, zIndex);

            menu.registerFeature(feat);
            featuresManager.addManagedFeature(feat);
            dispose();
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addFilter(Filter filter) {
        filters.add(filter);
        filtersLabel.setText(filtersLabel.getText() + filter.getClass().getName() + ", ");
    }
}
