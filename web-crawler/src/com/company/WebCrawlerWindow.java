package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.lang.Character.LINE_SEPARATOR;

public class WebCrawlerWindow extends JFrame {
    JTextField tfTargetSiteUrl;

    public WebCrawlerWindow() {
        setTitle("WebCrawlerWindow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(300, 300);
        setVisible(true);
        setLayout(new GridBagLayout());

        setTfTargetSiteUrl();
        JButton btnLaunchDownload = new JButton();
        JTextArea textArea = new JTextArea("HTML code?");

        btnLaunchDownload.setVisible(true);
        textArea.setVisible(true);


        btnLaunchDownload.setName("RunButton");
        textArea.setName("HtmlTextArea");

        btnLaunchDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(
                        downloadWebPageText(
                                tfTargetSiteUrl.getText()));
            }
        });

        textArea.setSize(200, 200);
        textArea.setEditable(false);
        textArea.setEnabled(false);
        textArea.setHighlighter(null);

        add(tfTargetSiteUrl);
        add(btnLaunchDownload);
        add(textArea);
    }

    private void setTfTargetSiteUrl() {
        tfTargetSiteUrl = new JTextField();
        tfTargetSiteUrl.setVisible(true);
        tfTargetSiteUrl.setName("UrlTextField");
        tfTargetSiteUrl.setSize(300, 15);
    }

    private String downloadWebPageText(final String url) {
        final InputStream inputStream;
        final BufferedReader reader;
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStream = new URL(url).openStream();
            reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
                stringBuilder.append(LINE_SEPARATOR);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }


        return stringBuilder.toString();
    }
}
