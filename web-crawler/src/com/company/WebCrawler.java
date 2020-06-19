package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler extends JFrame {
    private final JTextField urlTextField;
    private final JLabel titleLabel;
    private final JTextArea htmlTextArea;
    private final JButton runButton;

    public WebCrawler() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        setLayout(flowLayout);

        urlTextField = new JTextField();
        titleLabel = new JLabel();
        htmlTextArea = new JTextArea();
        runButton = new JButton("Parse");

        setUrlTextField();
        setTitleLabel();
        setHtmlTextArea();
        setBtnRun();

        add(urlTextField, BorderLayout.CENTER);
        add(runButton, BorderLayout.LINE_END);
        add(titleLabel, BorderLayout.LINE_START);
        add(htmlTextArea, BorderLayout.LINE_START);

        setWebCrawlerFrame();
    }

    private void setUrlTextField() {
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(350, 25));
        urlTextField.setVisible(true);
    }

    private void setTitleLabel() {
        titleLabel.setName("TitleLabel");
        titleLabel.setPreferredSize(new Dimension(500, 25));
        titleLabel.setVisible(true);
    }

    private void setHtmlTextArea() {
        htmlTextArea.setName("HtmlTextArea");
        htmlTextArea.setPreferredSize(new Dimension(490, 320));
        htmlTextArea.setEnabled(false);
        htmlTextArea.setEditable(false);
        htmlTextArea.setVisible(true);
    }

    private void setBtnRun() {
        runButton.setName("RunButton");
        runButton.setVisible(true);

        runButton.addActionListener(ev -> {
            htmlTextArea.setText(
                    downloadWebpage(
                            urlTextField.getText()));
            titleLabel.setText(parseTaHtmlWebpageTitle());
        });
    }

    private void setWebCrawlerFrame() {
        setTitle("WebCrawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500);
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
    }

    private String downloadWebpage(final String url) {
        String siteText = "";

        try (InputStream inputStream =
                     new BufferedInputStream(
                             new URL(url).openStream())) {

            siteText =
                    new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return siteText;
    }

    private String parseTaHtmlWebpageTitle() {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        Pattern javaPattern = Pattern.compile("(.*?<title>)(.*?)(</title>.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = javaPattern.matcher(text);

        String webpageTitle = "";
        if (matcher.find()) {
            webpageTitle = matcher.group(2);
        }

        scanner.close();
        return webpageTitle;
    }
}
