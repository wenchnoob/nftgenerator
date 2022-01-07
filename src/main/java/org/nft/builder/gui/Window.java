package org.nft.builder.gui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nft.builder.controllers.FeatureController;
import org.nft.builder.models.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class Window extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    @Getter
    private Hashtable<Feature, FeatureController> registeredFeatures;

    @NonNull
    private final Canvas canvas;

    @NonNull
    private final Menu menu;

    @PostConstruct
    private void init() {
        this.setName("NFT Builder");
        int width = 880, height = 650;
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));

        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(canvas, BorderLayout.CENTER);
        cont.add(menu, BorderLayout.PAGE_END);

        cont.add(menu.leftHandPane(), BorderLayout.LINE_START);
        cont.add(menu.rightHandPane(), BorderLayout.LINE_END);

        registeredFeatures = new Hashtable<>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setVisible(true);

        LOGGER.info("Initialized Window");
    }
}
