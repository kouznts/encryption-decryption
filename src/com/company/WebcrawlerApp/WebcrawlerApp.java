package com.company.WebcrawlerApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.company.Webcrawler.Webrcrawler.*;

public class WebcrawlerApp extends JFrame {
    private final List<String> urls = new CopyOnWriteArrayList<>();
    private final List<String> urlsTitles = new CopyOnWriteArrayList<>();

    private final WebcrawlerAppTableModel tableModel = new WebcrawlerAppTableModel(urls, urlsTitles);
    private final JTable table;
    private final JScrollPane tableScrollPane;

    private final JLabel urlLabel;
    private final JTextField urlTextField;
    private final JLabel urlTitleLabel;

    private final JLabel threadsLabel;
    private final JTextField threadsTextField;

    private final JToggleButton runButton;
    private final JTextField exportUrlTextField;
    private final JButton exportButton;

    public WebcrawlerApp() {
        setFrameLayout();

        urlLabel = new JLabel("Start URL:");
        urlTextField = new JTextField();
        runButton = new JToggleButton("Run");

        threadsLabel = new JLabel("Threads:");
        threadsTextField = new JTextField();

        urlTitleLabel = new JLabel();
        table = new JTable(tableModel);
        tableScrollPane = new JScrollPane(table);
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
        table.setName("TitlesTable");
        table.setPreferredSize(new Dimension(490, 320));
        table.setEnabled(false);
        table.setVisible(true);
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
        urlsTitles.clear();

        urls.add(urlTextField.getText());
        urls.addAll(parseLinks(urlTextField.getText()));
        urlsTitles.addAll(parseUrlsTitles(urls));
        deleteUrlsWithoutTitles(urls, urlsTitles);

        tableModel.fireTableDataChanged();
    }

    private void clickExportButton() {
        exportUrlsAndTitles(
                urls, urlsTitles, exportUrlTextField.getText()
                );
    }
}
