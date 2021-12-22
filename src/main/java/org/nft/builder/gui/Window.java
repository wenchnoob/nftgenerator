package org.nft.builder.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class Window extends JFrame {

    Canvas canvas;

    Menu menu;


    @Autowired
    public Window(Canvas canvas, Menu menu) {
        super("NFT Builder");

        this.canvas = canvas;
        this.menu = menu;

        int width = 880, height = 650;
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setVisible(true);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(menu, BorderLayout.PAGE_END);
    }

}
