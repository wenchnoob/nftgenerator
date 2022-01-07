package org.nft.builder.gui.input.compound;

import org.nft.builder.controllers.FeaturesManager;
import org.nft.builder.gui.Window;
import org.nft.builder.models.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

@Component
public class FeatureManagerUI extends JDialog {

    private FeaturesManager featuresManager;

    private Consumer<String> remove = featureName -> {
        featuresManager.removeManagedFeature(featureName);
        updateMenu();
    };

    private Consumer<String> edit = featureName -> {};

    private JPanel panel = new JPanel();

    @Autowired
    public FeatureManagerUI(Window win, FeaturesManager featuresManager) {
        super(win, "Feature Manager", ModalityType.DOCUMENT_MODAL);
        this.featuresManager = featuresManager;
        panel.setLayout(new GridLayout(0, 3));
        add(panel);
        setSize(new Dimension(300, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void updateMenu() {
        panel.removeAll();
        panel.setLayout(new GridLayout(0, 3));
        featuresManager.getManagedFeatures().forEach(this::addFeature);
        this.pack();
        this.revalidate();
        this.repaint();
    }

    private void addFeature(String name, Feature feature) {
        panel.add(new JLabel(name));
        panel.add(new JButton("EDIT") {
            {
                addActionListener(action -> {
                    edit.accept(name);
                });
            }
        });
        panel.add(new JButton("DELETE") {
            {
                addActionListener(action -> {
                    remove.accept(name);
                });
            }
        });
    }
}
