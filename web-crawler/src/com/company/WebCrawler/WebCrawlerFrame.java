package com.company.WebCrawler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.WebCrawling.WebCrawling.*;

public class WebCrawlerFrame extends JFrame {
    private final List<String> urls = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    private final WebCrawlerTableModel tableModel = new WebCrawlerTableModel(urls, titles);
    private final JTable urlsAndTitlesTable;
    private final JScrollPane tableScrollPane;

    private final JLabel urlLabel;
    private final JTextField urlTextField;
    private final JLabel urlTitleLabel;

    private final JLabel threadsLabel;
    private final JTextField threadsTextField;

    private final JToggleButton runButton;
    private final JTextField exportUrlTextField;
    private final JButton exportButton;

    public WebCrawlerFrame() {
        setFrameLayout();

        urlLabel = new JLabel("Start URL:");
        urlTextField = new JTextField();
        runButton = new JToggleButton("Run");

        threadsLabel = new JLabel("Threads:");
        threadsTextField = new JTextField();

        urlTitleLabel = new JLabel();
        urlsAndTitlesTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(urlsAndTitlesTable);
        exportUrlTextField = new JTextField();
        exportButton = new JButton("Export");

        setFrameElements();
        addElementsToFrame();

        setFrame();
    }

    private void setFrameLayout() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        setLayout(flowLayout);
    }

    private void setFrameElements() {
        setUrlTextField();
        setThreadsTextField();

        setUrlTitleLabel();
        setUrlsAndTitlesTable();
        setTableScrollPane();
        setRunButton();
        setExportUrlTextField();
        setExportButton();
    }

    private void addElementsToFrame() {
        add(urlLabel, BorderLayout.LINE_START);
        add(urlTextField, BorderLayout.CENTER);
        add(runButton, BorderLayout.LINE_END);

        add(threadsLabel, BorderLayout.LINE_START);
        add(threadsTextField, BorderLayout.CENTER);

        add(urlTitleLabel, BorderLayout.LINE_START);
        add(tableScrollPane, BorderLayout.LINE_START);
        add(exportUrlTextField, BorderLayout.CENTER);
        add(exportButton, BorderLayout.LINE_END);
    }

    private void setUrlTextField() {
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(350, 25));
        urlTextField.setVisible(true);
    }

    private void setThreadsTextField() {
        threadsTextField.setName("ThreadsTextField");
        threadsTextField.setPreferredSize(new Dimension(350, 25));
        threadsTextField.setVisible(true);
    }

    private void setUrlTitleLabel() {
        urlTitleLabel.setName("TitleLabel");
        urlTitleLabel.setPreferredSize(new Dimension(500, 25));
        urlTitleLabel.setVisible(true);
    }

    private void setUrlsAndTitlesTable() {
        urlsAndTitlesTable.setName("TitlesTable");
        urlsAndTitlesTable.setPreferredSize(new Dimension(490, 320));
        urlsAndTitlesTable.setEnabled(false);
        urlsAndTitlesTable.setVisible(true);
    }

    private void setTableScrollPane() {
        tableScrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tableScrollPane.setVerticalScrollBarPolicy(
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

        exportButton.addActionListener(ev -> clickExportButton());
    }

    private void setFrame() {
        setTitle("WebCrawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 600);
        setPreferredSize(new Dimension(500, 600));
        setVisible(true);
    }

    private void clickRunButton() {
        urlTitleLabel.setText(
                parseTitleFromHtmlCode(parseHtmlCode(urlTextField.getText()))
        );

        urls.clear();
        titles.clear();

        urls.add(urlTextField.getText());
        urls.addAll(parseLinks(urlTextField.getText()));
        titles.addAll(parseTitles(urls));
        deleteUrlsWithoutTitles(urls, titles);

        tableModel.fireTableDataChanged();
    }

    private void clickExportButton() {
        exportUrlsAndTitles(
                exportUrlTextField.getText(), urls, titles
        );
    }
}
