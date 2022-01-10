package org.nft.builder.gui.input.compound;

import org.nft.builder.controllers.FeaturesManager;
import org.nft.builder.gui.input.InputFactory;
import org.nft.builder.imageops.OpsFactory;
import org.nft.builder.imageops.ImageOp;
import org.nft.builder.imageops.utils.IO;
import org.nft.builder.managers.ContextManager;
import org.nft.builder.models.Feature;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import org.nft.builder.gui.Window;
import org.nft.builder.gui.Menu;

public class FeatureSpecInput extends JDialog {

    private Menu menu;
    private FeaturesManager featuresManager;
    private File srcFile;
    private Feature feature;

    private JPanel contRoot = new JPanel();

    JLabel locationLabel = new JLabel("Location Selected: ");
    JLabel selectedLocation = new JLabel();

    JLabel name = new JLabel("Name: ");
    JTextField nameIn = new JTextField("");

    JLabel xoffset = new JLabel("X-Offset");
    JTextField xoffsetIn = InputFactory.numericInput("0");

    JLabel yoffset = new JLabel("Y-Offset");
    JTextField yoffsetIn = InputFactory.numericInput("0");

    JLabel zIndex = new JLabel("Z-Index");
    JTextField zIndexIn = InputFactory.numericInput("0");

    JButton submit = new JButton("Submit");

    JButton addFilters = new JButton("Add Filters");
    JLabel filtersLabel = new JLabel("Filters: ");

    List<ImageOp> imageOps = new ArrayList<>();

    private ActionListener addFeature = action -> {
        String name = nameIn.getText();
        int xOffset = Integer.parseInt(xoffsetIn.getText());
        int yOffset = Integer.parseInt(yoffsetIn.getText());
        int zIndex = Integer.parseInt(zIndexIn.getText());
        Feature feat = new Feature(srcFile, name, xOffset, yOffset, zIndex, imageOps, IO.emptyImage());
        featuresManager.addManagedFeature(feat);
        dispose();
    };

    private ActionListener editFeature = action -> {
        if (action.getSource() == null || action.getActionCommand().equals("refresh")) return;
        menu.unregisterFeature(feature);

        String name = nameIn.getText();
        int xOffset = Integer.parseInt(xoffsetIn.getText());
        int yOffset = Integer.parseInt(yoffsetIn.getText());
        int zIndex = Integer.parseInt(zIndexIn.getText());

        feature.setName(name);
        feature.setXOffset(xOffset);
        feature.setYOffset(yOffset);
        feature.setZIndex(zIndex);

        menu.registerFeature(feature);
        dispose();
    };

    public FeatureSpecInput(Window win, Menu menu, FeaturesManager featuresManager, File srcFile, Feature feature, boolean isNew, Dialog.ModalityType modalityType, ContextManager contextManager) {
        super(win, (isNew ? "Add " : "Edit ") + "Feature", modalityType);
        this.menu = menu;
        this.featuresManager = featuresManager;
        this.srcFile = srcFile;
        this.feature = feature;

        contRoot.setLayout(new GridLayout(0, 2));

        contRoot.add(locationLabel);
        contRoot.add(selectedLocation);
        contRoot.add(name);
        contRoot.add(nameIn);
        contRoot.add(xoffset);
        contRoot.add(xoffsetIn);
        contRoot.add(yoffset);
        contRoot.add(yoffsetIn);
        contRoot.add(zIndex);
        contRoot.add(zIndexIn);


        if (isNew) {
            selectedLocation.setText(srcFile.getAbsolutePath());

            selectedLocation.setBorder(new EmptyBorder(10, 10, 10, 10));
            locationLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

            contRoot.add(new JLabel(""));
            contRoot.add(addFilters);
            contRoot.add(filtersLabel);


            addFilters.addActionListener(action -> {
                String[] filters = contextManager.getBeanNames(ImageOp.class);
                Object in = JOptionPane.showInputDialog(this, "Select a Filter", "Filter Selection", JOptionPane.QUESTION_MESSAGE, null, filters, "");
                if (in == null) return;
                String input = (String) in;
                String[] args;

                switch (input) {
                    case "Blue Light Filter" -> {
                        args = new String[1];
                    }
                    case "Rotation" -> {
                        args = new String[1];
                        Object rot = JOptionPane.showInputDialog(this, "Choose Degree of Rotation", "Config", JOptionPane.QUESTION_MESSAGE, null, new String[]{"90", "180", "270", "360"}, "");
                        if (rot == null) args[0] = "360";
                        else args[0] = (String)rot;
                    }
                    default -> { args = new String[0];}
                }

                addFilter(OpsFactory.of((String) in, args));
            });

            submit.addActionListener(addFeature);
        } else {
            if (feature == null) throw new IllegalStateException("Feature cannot be null, when editing");
            selectedLocation.setText(feature.getSrcFile().toString());
            nameIn.setText(feature.getName());
            xoffsetIn.setText(String.valueOf(feature.getXOffset()));
            yoffsetIn.setText(String.valueOf(feature.getYOffset()));
            zIndexIn.setText(String.valueOf(feature.getZIndex()));

            //contRoot.revalidate();
            //contRoot.repaint();

            submit.addActionListener(editFeature);
        }
        contRoot.add(new JLabel(""));
        contRoot.add(new JLabel(""));
        contRoot.add(submit);

        add(contRoot);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addFilter(ImageOp imageOp) {
        System.out.println(imageOp);
        if (imageOp == null) return;
        imageOps.add(imageOp);
        filtersLabel.setText(filtersLabel.getText() + imageOp.getClass().getName() + ", ");
    }
}
