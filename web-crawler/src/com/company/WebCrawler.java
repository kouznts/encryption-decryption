package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.WebCrawling.downloadWebpage;
import static com.company.WebCrawling.parseWebpageTitle;

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
}
