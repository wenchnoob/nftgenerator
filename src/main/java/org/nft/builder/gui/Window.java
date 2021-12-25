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

        System.out.println(menu);

        int width = 880, height = 650;
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));

        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(canvas, BorderLayout.CENTER);
        cont.add(menu, BorderLayout.PAGE_END);
        cont.setBackground(Color.BLUE);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setVisible(true);
    }

}
