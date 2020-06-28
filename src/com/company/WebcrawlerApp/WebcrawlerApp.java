package com.company.WebcrawlerApp;

import com.company.Webcrawling.Webcrawling;

import javax.swing.*;
import java.awt.*;

import static com.company.Webcrawling.WebParsing.parseHtmlCode;
import static com.company.Webcrawling.WebParsing.parseTitleFromHtmlCode;

public class WebcrawlerApp extends JFrame {

    private final Webcrawling model = new Webcrawling();

    private final JLabel urlLabel;
    private final JTextField urlTextField;
    private final JButton runButton;

    private final JLabel threadsLabel;
    private final JTextField threadsTextField;

    private final JLabel depthLabel;
    private final JTextField depthTextField;
    private final JCheckBox depthCheckBox;

    private final JLabel secondsLimitLabel;
    private final JTextField secondsLimitTextField;
    private final JCheckBox secondsLimitCheckBox;

    private final JLabel urlTitleLabel;
    private final JTextField exportUrlTextField;
    private final JButton exportButton;

    public WebcrawlerApp(WebcrawlerAppSettings settings) {
        setFrameLayout();

        urlLabel = new JLabel("URL:");
        urlTextField = new JTextField();
        runButton = new JButton("Run");

        threadsLabel = new JLabel("Threads:");
        threadsTextField = new JTextField();

        depthLabel = new JLabel("Depth:");
        depthTextField = new JTextField();
        depthCheckBox = new JCheckBox("Enabled", true);

        secondsLimitLabel = new JLabel("Limit seconds");
        secondsLimitTextField = new JTextField();
        secondsLimitCheckBox = new JCheckBox("Enabled", true);

        urlTitleLabel = new JLabel();
        exportUrlTextField = new JTextField();
        exportButton = new JButton("Export");

        setFrameElements(settings);
        addElementsToFrame();

        setFrame(settings.getFrameWidth(), settings.getFrameHeight());
    }

    private void setFrameLayout() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        setLayout(flowLayout);
    }

    private void setFrameElements(WebcrawlerAppSettings settings) {
        setUrlTextField(settings.getTextFieldWidth(), settings.getTextFieldHeight());
        setRunButton();

        setThreadsTextField(settings.getTextFieldWidth(), settings.getTextFieldHeight());

        setDepthTextField(settings.getTextFieldWidth(), settings.getTextFieldHeight());
        setDepthCheckBox();

        setSecondsLimitTextField(settings.getTextFieldWidth(), settings.getTextFieldHeight());
        setSecondsLimitCheckBox();

        setUrlTitleLabel(settings.getTextFieldWidth(), settings.getTextFieldHeight());
        setExportUrlTextField(settings.getTextFieldWidth(), settings.getTextFieldHeight());
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
        add(depthCheckBox, BorderLayout.LINE_END);

        add(secondsLimitLabel, BorderLayout.LINE_START);
        add(secondsLimitTextField, BorderLayout.CENTER);
        add(secondsLimitCheckBox, BorderLayout.LINE_END);

        add(urlTitleLabel, BorderLayout.LINE_START);
        add(exportUrlTextField, BorderLayout.CENTER);
        add(exportButton, BorderLayout.LINE_END);
    }

    private void setUrlTextField(final int width, final int height) {
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(width, height));
        urlTextField.setVisible(true);
        urlTextField.setText("https://hi.hyperskill.org/");
    }

    private void setRunButton() {
        runButton.setName("RunButton");
        runButton.setVisible(true);

        runButton.addActionListener(ev -> clickRunButton());
    }

    private void setThreadsTextField(final int width, final int height) {
        threadsTextField.setName("ThreadsTextField");
        threadsTextField.setPreferredSize(new Dimension(width, height));
        threadsTextField.setVisible(true);
        threadsTextField.setText("1");
    }

    private void setDepthTextField(final int width, final int height) {
        depthTextField.setName("DepthTextField");
        depthTextField.setPreferredSize(new Dimension(width, height));
        depthTextField.setVisible(true);
        depthTextField.setText("1");
    }

    private void setDepthCheckBox() {
        depthCheckBox.addChangeListener(ev ->
                depthTextField.setEnabled(depthCheckBox.isSelected())
        );
    }

    private void setSecondsLimitTextField(final int width, final int height) {
        secondsLimitTextField.setName("SecondsTextField");
        secondsLimitTextField.setPreferredSize(new Dimension(width, height));
        secondsLimitTextField.setVisible(true);
        secondsLimitTextField.setText(String.valueOf(model.getSecondsLimit()));
    }

    private void setSecondsLimitCheckBox() {
        secondsLimitCheckBox.addChangeListener(ev -> {
            if (secondsLimitCheckBox.isSelected()) {
                secondsLimitTextField.setEnabled(true);
            } else {
                secondsLimitTextField.setEnabled(false);
                model.resetSecondsLimit();
                secondsLimitTextField.setText(String.valueOf(model.getSecondsLimit()));
            }
        });
    }

    private void setUrlTitleLabel(final int width, final int height) {
        urlTitleLabel.setName("TitleLabel");
        urlTitleLabel.setPreferredSize(new Dimension(width, height));
        urlTitleLabel.setVisible(true);
    }

    private void setExportUrlTextField(final int width, final int height) {
        exportUrlTextField.setName("ExportUrlTextField");
        exportUrlTextField.setPreferredSize(new Dimension(width, height));
        exportUrlTextField.setVisible(true);
        exportUrlTextField.setText("D:\\webcrawling.txt");
    }

    private void setExportButton() {
        exportButton.setName("ExportButton");
        exportButton.setVisible(true);

        exportButton.addActionListener(ev -> clickExportButton());
    }

    private void setFrame(final int width, final int height) {
        setTitle("WebCrawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    private void clickRunButton() {
        urlTitleLabel.setText(
                parseTitleFromHtmlCode(parseHtmlCode(urlTextField.getText()))
        );

        model.setCrawlingThreadsNumber(Integer.parseInt(threadsTextField.getText()));
        model.setDepthNumber(Integer.parseInt(depthTextField.getText()));
        model.setSecondsLimit(Integer.parseInt(depthTextField.getText()));

        model.run(urlTextField.getText());

        model.fireTableDataChanged();
    }

    private void clickExportButton() {
        model.export(exportUrlTextField.getText());
    }
}
