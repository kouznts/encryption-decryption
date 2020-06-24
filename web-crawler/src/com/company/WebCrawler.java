package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.WebCrawling.*;

public class WebCrawler extends JFrame {
    private final List<String> urls = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();
    private final WebCrawlerTableModel tableModel = new WebCrawlerTableModel(urls, titles);

    private final JTextField urlTextField;
    private final JLabel titleLabel;
    private final JTable titlesTable;
    private final JScrollPane scrollPane;
    private final JButton runButton;
    private final JTextField exportUrlTextField;
    private final JButton exportButton;

    public WebCrawler() {
        setFrameLayout();

        urlTextField = new JTextField();
        titleLabel = new JLabel();
        titlesTable = new JTable(tableModel);
        scrollPane = new JScrollPane(titlesTable);
        runButton = new JButton("Parse");
        exportUrlTextField = new JTextField();
        exportButton = new JButton("Export");

        setFrameElements();
        addElementsToFrame();

        setWebCrawlerFrame();
    }

    private void setFrameLayout() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        setLayout(flowLayout);
    }

    private void setFrameElements() {
        setUrlTextField();
        setTitleLabel();
        setTitlesTable();
        setScrollPane();
        setRunButton();
        setExportUrlTextField();
        setExportButton();
    }

    private void addElementsToFrame() {
        add(urlTextField, BorderLayout.CENTER);
        add(runButton, BorderLayout.LINE_END);
        add(titleLabel, BorderLayout.LINE_START);
        add(scrollPane, BorderLayout.LINE_START);
        add(exportUrlTextField, BorderLayout.CENTER);
        add(exportButton, BorderLayout.LINE_END);
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

    private void setRunButton() {
        runButton.setName("RunButton");
        runButton.setVisible(true);

        runButton.addActionListener(ev -> clickRunButton());
    }

    private void setExportUrlTextField() {
        exportUrlTextField.setName("ExportUrlTextField");
        exportUrlTextField.setPreferredSize(new Dimension(350, 25));
        exportUrlTextField.setVisible(true);
    }

    private void setExportButton() {
        exportButton.setName("ExportButton");
        exportButton.setVisible(true);

        exportButton.addActionListener(ev -> exportWebCrawling());
    }

    private void setWebCrawlerFrame() {
        setTitle("WebCrawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500);
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
    }

    private void clickRunButton() {
        titleLabel.setText(
                parseWebpageTitle(parseWebpageHtmlCode(urlTextField.getText()))
        );

        urls.clear();
        titles.clear();

        urls.add(urlTextField.getText());
        urls.addAll(parseWebpageLinks(urlTextField.getText()));
        titles.addAll(parseWebpagesTitles(urls));
        deleteUrlsWithoutTitles();

        tableModel.fireTableDataChanged();
    }

    private void deleteUrlsWithoutTitles() {
        List<Integer> deletingUrlsIndexes = new ArrayList<>();

        for (int i = 0; i < urls.size(); ++i) {
            if (titles.get(i).equals("")) {
                deletingUrlsIndexes.add(i);
            }
        }

        for (int i = 0, deletingIndex; i < deletingUrlsIndexes.size(); ++i) {
            deletingIndex = deletingUrlsIndexes.get(i);
            urls.remove(deletingIndex);
            titles.remove(deletingIndex);
        }
    }
}
