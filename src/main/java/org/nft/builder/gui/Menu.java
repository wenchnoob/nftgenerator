package org.nft.builder.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.controllers.FeatureController;
import org.nft.builder.gui.input.InputFactory;
import org.nft.builder.gui.input.compound.FeatureSpecInput;
import org.nft.builder.managers.ContextManager;
import org.nft.builder.models.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class Menu extends JPanel {

    @NonNull
    private final Canvas canvas;

    private final String head = "src/main/resources/images/heads/png";
    private final String torso = "src/main/resources/images/torsos/png";
    private final String legs = "src/main/resources/images/legs/png";
    private final String arms = "src/main/resources/images/arms/png";

    @NonNull
    private ContextManager contextManager;

    @PostConstruct
    public void setup() {
        setPreferredSize(new Dimension(500, 100));

        add(manageFeaturesButton());
        add(addFeatureButton());
        add(saveButton());
        add(shuffleButton());
        add(shuffleAllButton());
    }

    public JButton shuffleButton() {
        return new JButton("Shuffle") {
            {
                addActionListener(action -> {
                    Window win = (Window) contextManager.getBean("window");
                    win.getRegisteredFeatures().values().forEach(featureController -> {
                        featureController.getRandom();
                        canvas.repaint();
                    });
                });
            }
        };
    }

    public JButton shuffleAllButton() {
        return new JButton("Shuffle All") {
            {
                addActionListener(action -> {
                    JOptionPane.showMessageDialog(null, "First choose the folder that all the images will be saved.");
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setCurrentDirectory(new File(new File("./").getAbsolutePath() + "/src/main/resources/images"));
                    chooser.showSaveDialog(null);
                    File chosen = chooser.getSelectedFile();
                    if (chosen == null) return;
                    if (!chosen.exists()) chosen.mkdir();
                    if (chosen.isDirectory()) canvas.shuffleAll(chosen);
                });
            }
        };
    }

    public JButton manageFeaturesButton() {
        return new JButton("Manage Features") {
            {

            }
        };
    }

    public JButton addFeatureButton() {
        return new JButton("Add a Feature") {
            {
                addActionListener(click -> {
                    JOptionPane.showMessageDialog(null, "First choose the folder that all the images for your feature are in.");
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setCurrentDirectory(new File(new File("./").getAbsolutePath() + "/src/main/resources/images"));
                    chooser.showOpenDialog(null);
                    File chosen = chooser.getSelectedFile();

                    if (chosen == null) return;
                    new FeatureSpecInput((Window) contextManager.getBean("window"), chosen, "Feature Setup", Dialog.ModalityType.DOCUMENT_MODAL);
                });
            }
        };
    }

    public JButton saveButton() {
        return new JButton("Save current Canvas") {
            {
                addActionListener(action -> {
                    JFileChooser chooser = new JFileChooser(new File("./").getAbsolutePath() + "/src/main/resources/images");
                    chooser.showSaveDialog(null);
                    File chosen = chooser.getSelectedFile();
                    canvas.saveTo(chosen);
                });
            }
        };
    }

}
