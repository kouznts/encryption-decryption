package com.company;

import javax.swing.*;
import java.awt.*;

import static com.company.WebParsing.parseHtmlCode;
import static com.company.WebParsing.parseTitleFromHtmlCode;

public class WebcrawlerApp extends JFrame {

    private final Webcrawling model = new Webcrawling();

    private final JLabel urlLabel;
    private final JTextField urlTextField;
    private final JLabel urlTitleLabel;

    private final JLabel threadsLabel;
    private final JTextField threadsTextField;

    private final JLabel depthLabel;
    private final JTextField depthTextField;

    private final JButton runButton;
    private final JTextField exportUrlTextField;
    private final JButton exportButton;

    public WebcrawlerApp() {
        setFrameLayout();

        urlLabel = new JLabel("Start URL:");
        urlTextField = new JTextField();
        runButton = new JButton("Run");

        threadsLabel = new JLabel("Threads:");
        threadsTextField = new JTextField();

        depthLabel = new JLabel("Depth:");
        depthTextField = new JTextField();

        urlTitleLabel = new JLabel();
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
        setDepthTextField();

        setUrlTitleLabel();
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

        add(depthLabel, BorderLayout.LINE_START);
        add(depthTextField, BorderLayout.CENTER);

        add(urlTitleLabel, BorderLayout.LINE_START);
        add(exportUrlTextField, BorderLayout.CENTER);
        add(exportButton, BorderLayout.LINE_END);
    }

    private void setUrlTextField() {
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(350, 25));
        urlTextField.setVisible(true);
        urlTextField.setText("https://hi.hyperskill.org/");
    }

    private void setThreadsTextField() {
        threadsTextField.setName("ThreadsTextField");
        threadsTextField.setPreferredSize(new Dimension(350, 25));
        threadsTextField.setVisible(true);
        threadsTextField.setText("1");
    }

    private void setDepthTextField() {
        depthTextField.setName("DepthTextField");
        depthTextField.setPreferredSize(new Dimension(350, 25));
        depthTextField.setVisible(true);
        depthTextField.setText("1");
    }

    private void setUrlTitleLabel() {
        urlTitleLabel.setName("TitleLabel");
        urlTitleLabel.setPreferredSize(new Dimension(500, 25));
        urlTitleLabel.setVisible(true);
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

        model.setDepthNumber(Integer.parseInt(depthTextField.getText()));
        model.setCrawlingThreadsNumber(Integer.parseInt(threadsTextField.getText()));

        model.run(urlTextField.getText());

        model.fireTableDataChanged();
    }

    private void clickExportButton() {
        model.export(exportUrlTextField.getText());
    }
}
