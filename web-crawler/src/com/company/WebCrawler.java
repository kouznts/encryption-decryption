package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebCrawler extends JFrame {
    JPanel panel;
    JTextField tfUrl;
    JButton btnRun;
    JTextArea taHtml;

    public WebCrawler() {
        setTfUrl();
        setTaHtml();
        setBtnRun();

        setPanel();
        add(panel);

        setWebCrawlerFrame();
    }

    private void setTfUrl() {
        tfUrl = new JTextField();
        tfUrl.setName("UrlTextField");
        tfUrl.setPreferredSize(new Dimension(100, 30));
        tfUrl.setVisible(true);
    }

    private void setTaHtml() {
        taHtml = new JTextArea("HTML code?");
        taHtml.setName("HtmlTextArea");
        taHtml.setPreferredSize(new Dimension(100, 100));
        taHtml.setEnabled(false);
        taHtml.setEditable(false);
        taHtml.setHighlighter(null);
        taHtml.setVisible(true);
    }

    private void setBtnRun() {
        btnRun = new JButton();
        btnRun.setName("RunButton");
        btnRun.setPreferredSize(new Dimension(100, 100));
        btnRun.setVisible(true);

        btnRun.addActionListener(ev ->
                taHtml.setText(
                        downloadWebpage(tfUrl.getText())));
    }

    private void setPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(tfUrl);
        panel.add(taHtml);
        panel.add(btnRun);
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
