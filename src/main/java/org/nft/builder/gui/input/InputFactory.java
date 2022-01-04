package org.nft.builder.gui.input;

import org.nft.builder.gui.input.filters.IntFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;

public class InputFactory {

    public static JTextField numericInput(String text) {
        JTextField in = new JTextField(text);
        PlainDocument doc = (PlainDocument) in.getDocument();
        doc.setDocumentFilter(new IntFilter());
        return in;
    }

}
