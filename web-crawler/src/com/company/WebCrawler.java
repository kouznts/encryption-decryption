package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler extends JFrame {
    private final List<String> urls = new ArrayList<String>();
    private final List<String> titles = new ArrayList<String>();
    private final WebCrawlerTableModel tableModel = new WebCrawlerTableModel(urls, titles);

    private final JTextField urlTextField;
    private final JLabel titleLabel;
    private final JTable titlesTable;
    private final JScrollPane scrollPane;
    private final JButton runButton;

    public WebCrawler() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        setLayout(flowLayout);

        urlTextField = new JTextField();
        titleLabel = new JLabel();
        titlesTable = new JTable(tableModel);
        scrollPane = new JScrollPane(titlesTable);
        runButton = new JButton("Parse");

        setUrlTextField();
        setTitleLabel();
        setTitlesTable();
        setBtnRun();

        add(urlTextField, BorderLayout.CENTER);
        add(runButton, BorderLayout.LINE_END);
        add(titleLabel, BorderLayout.LINE_START);
        add(scrollPane, BorderLayout.LINE_START);

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

    private void setTitlesTable() {
        titlesTable.setName("TitlesTable");
        titlesTable.setPreferredSize(new Dimension(490, 320));
        titlesTable.setEnabled(false);
        titlesTable.setVisible(true);
    }

    private void setScrollPane() {
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void setBtnRun() {
        runButton.setName("RunButton");
        runButton.setVisible(true);

        runButton.addActionListener(ev -> {
            titleLabel.setText(
                    parseWebpageTitle(
                            urlTextField.getText()
                    )
            );

            titlesTable.setText(downloadWebpage(
                    urlTextField.getText()));
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

        try {
            URLConnection urlConnection = new URL(url).openConnection();

            if (urlConnection.getContentType().equals("text/html")) {
                try (InputStream inputStream =
                             new BufferedInputStream(
                                     urlConnection.getInputStream())) {

                    siteText =
                            new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return siteText;
    }

    private String parseWebpageTitle(String webpageHtmlCode) {
        Pattern javaPattern = Pattern.compile("(.*?<title>)(.*?)(</title>.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = javaPattern.matcher(webpageHtmlCode);

        String webpageTitle = "";
        if (matcher.find()) {
            webpageTitle = matcher.group(2);
        }

        return webpageTitle;
    }
}
