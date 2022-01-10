package org.nft.builder.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.controllers.FeatureController;
import org.nft.builder.controllers.FeaturesManager;
import org.nft.builder.gui.input.compound.FeatureManagerUI;
import org.nft.builder.gui.input.compound.FeatureSpecInput;
import org.nft.builder.managers.ContextManager;
import org.nft.builder.models.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class Menu extends JPanel {

    @NonNull
    private final Canvas canvas;

    @NonNull
    private ContextManager contextManager;

    private final JPanel leftPanel = new JPanel() {
        {
            setLayout(new GridLayout(0, 1));
        }
    };

    private final JPanel rightPanel = new JPanel() {
        {
            setLayout(new GridLayout(0, 1));
        }
    };

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
                    canvas.getFeatures().forEach(feat -> new FeatureController(feat, canvas).getRandom());
                    canvas.repaint();
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
                addActionListener(action -> {
                    FeatureManagerUI ui = (FeatureManagerUI) contextManager.getBean("featureManagerUI");
                    ui.updateMenu();
                    ui.setVisible(true);
                });
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
                    inputDialog(chosen);
                });
            }
        };
    }

    private void inputDialog(File chosen) {
        new FeatureSpecInput((Window) contextManager.getBean("window"), this, (FeaturesManager) contextManager.getBean("featuresManager"), chosen, null, true, Dialog.ModalityType.DOCUMENT_MODAL);
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

    public JPanel leftHandPane() {
        return leftPanel;
    }

    public JPanel rightHandPane() {
        return rightPanel;
    }

    public boolean registerFeature(Feature feat) {
        FeatureController controller = new FeatureController(feat, canvas);

        canvas.addFeature(feat);
        contextManager.addBean(controller, feat.getName());

        JButton prevButton = controller.getPrevButton();
        JButton nextButton = controller.getNextButton();

        leftPanel.add(prevButton);
        leftPanel.revalidate();
        leftPanel.repaint();
        rightPanel.add(nextButton);
        rightPanel.revalidate();
        rightPanel.repaint();
        repaint();
        return true;
    }

    public boolean unregisterFeature(Feature feat) {
        //FeatureController controller = registeredFeatures.get(feat);
        canvas.removeFeature(feat);
        leftPanel.removeAll();
        rightPanel.removeAll();
        leftPanel.revalidate();
        rightPanel.revalidate();
        leftPanel.repaint();
        rightPanel.repaint();
        Set<Feature> featureSet = canvas.getFeatures();
        new HashSet<>(featureSet).forEach(this::registerFeature);
        return false;
    }
}
