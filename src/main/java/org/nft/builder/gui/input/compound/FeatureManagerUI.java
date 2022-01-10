package org.nft.builder.gui.input.compound;

import lombok.NonNull;
import org.nft.builder.controllers.FeaturesManager;
import org.nft.builder.gui.Menu;
import org.nft.builder.gui.Window;
import org.nft.builder.managers.ContextManager;
import org.nft.builder.models.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

@Component
public class FeatureManagerUI extends JDialog {


    private FeaturesManager featuresManager;

    private ContextManager contextManager;

    private Consumer<String> remove = featureName -> {
        featuresManager.removeManagedFeature(featureName);
        updateMenu();
    };

    private Consumer<String> edit = featureName -> {
        Feature feature = featuresManager.getManagedFeatures().get(featureName);
        Menu menu = (Menu) contextManager.getBean("menu");
        new FeatureSpecInput((Window) contextManager.getBean("window"),
                menu,
                (FeaturesManager) contextManager.getBean("featuresManager"),
                feature.getSrcFile(), feature, false, Dialog.ModalityType.DOCUMENT_MODAL);
        featuresManager.removeManagedFeature(featureName);
        featuresManager.addManagedFeature(feature);
        updateMenu();
    };

    private JPanel panel = new JPanel();

    @Autowired
    public FeatureManagerUI(Window win, FeaturesManager featuresManager, ContextManager contextManager) {
        super(win, "Feature Manager", ModalityType.DOCUMENT_MODAL);
        this.featuresManager = featuresManager;
        this.contextManager = contextManager;
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
        panel.invalidate();
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
