package com.company;

import javax.swing.*;

public class WebCrawler extends JFrame {
    public WebCrawler() {
        setTitle("Simple Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(300, 300);
        setVisible(true);
        setLayout(null);

        JTextArea textArea = new JTextArea("HTML code?");
        textArea.setName("TextArea");
        textArea.setSize(200, 200);
        textArea.setEditable(false);
        textArea.setEnabled(false);
        textArea.setHighlighter(null);

        add(textArea);
    }
}
