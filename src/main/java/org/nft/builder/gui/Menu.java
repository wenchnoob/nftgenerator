package org.nft.builder.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Component
public class Menu extends JPanel {

    Canvas canvas;

    String head = "C:\\Users\\wench\\IdeaProjects\\builder\\src\\main\\resources\\images\\heads";
    String torso = "C:\\Users\\wench\\IdeaProjects\\builder\\src\\main\\resources\\images\\torsos";
    String legs = "C:\\Users\\wench\\IdeaProjects\\builder\\src\\main\\resources\\images\\legs";

    @Autowired
    public Menu(Canvas canvas) {
        this.canvas = canvas;
        add(picImageButton());
        add(saveButton());
        add(shuffleButton());
    }

    public JButton shuffleButton() {
        return new JButton("Shuffle") {
            {
                addActionListener(action -> {
                    canvas.clear();

                    File randHead = randFromFolder(head);
                    File randTorso = randFromFolder(torso);
                    File randLegs = randFromFolder(legs);

                    System.out.println(randHead);
                    System.out.println(randTorso);
                    System.out.println(randLegs);

                    if (randHead != null) canvas.addPicture(randHead);
                    if (randTorso != null) canvas.addPicture(randTorso);
                    if (randLegs != null) canvas.addPicture(randLegs);
                });
            }
        };
    }

    private File randFromFolder(String folder) {
        File[] files = Path.of(folder).toFile().listFiles();
        if (files.length <= 0) return null;
        int idx = new Random().nextInt(files.length);
        return files[idx];
    }

    public JButton picImageButton() {
        return new JButton("Add an Image") {
            {
                addActionListener(action -> {
                    JFileChooser chooser = new JFileChooser(new File("./").getAbsolutePath() + "/src/main/resources/images");
                    chooser.showOpenDialog(null);
                    File chosen = chooser.getSelectedFile();
                    if (chosen == null) return;
                    canvas.addPicture(chosen);
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
                    System.out.println(chosen);
                    canvas.saveTo(chosen);
                });
            }
        };
    }

}
