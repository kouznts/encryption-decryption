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
    JTextField urlTextField;
    JLabel titleLabel;
    JTextArea htmlTextArea;
    JButton runButton;

    public WebCrawler() {
        setUrlTextField();
        setTitleLabel();
        setHtmlTextArea();
        setBtnRun();

        setPanel();
        add(panel);

        setWebCrawlerFrame();
    }

    private void setUrlTextField() {
        urlTextField = new JTextField();
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(100, 30));
        urlTextField.setVisible(true);
    }

    private void setTitleLabel() {
        titleLabel = new JLabel();
        titleLabel.setName("TitleLabel");
        titleLabel.setPreferredSize(new Dimension(100, 30));
        titleLabel.setVisible(true);
    }

    private void setHtmlTextArea() {
        htmlTextArea = new JTextArea("HTML code?");
        htmlTextArea.setName("HtmlTextArea");
        htmlTextArea.setPreferredSize(new Dimension(100, 100));
        htmlTextArea.setEnabled(false);
        htmlTextArea.setEditable(false);
        htmlTextArea.setHighlighter(null);
        htmlTextArea.setVisible(true);
    }

    private void setBtnRun() {
        runButton = new JButton();
        runButton.setName("RunButton");
        runButton.setPreferredSize(new Dimension(100, 100));
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
