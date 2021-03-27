package com.company.Webcrawling;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static com.company.Webcrawling.WebParsing.*;
import static com.company.Webcrawling.Webcrawling.MAX_DEPTH_NUMBER;
import static com.company.Webcrawling.Webcrawling.MIN_DEPTH_NUMBER;

public class WebcrawlingThread extends Thread {

    private final Queue<String> tasks;
    private final Set<String> processedUrls;

    private final List<String> urls;
    private final List<String> urlsTitles;

    private final int depthNumber;

    public WebcrawlingThread(
            @NotNull final List<String> urls, @NotNull final List<String> urlsTitles,
            @NotNull final Queue<String> tasks, @NotNull final Set<String> processedUrls,
            final int depthNumber) {

        if (depthNumber < MIN_DEPTH_NUMBER
                || depthNumber > MAX_DEPTH_NUMBER) {
            throw new IndexOutOfBoundsException();
        }

        this.tasks = tasks;
        this.processedUrls = processedUrls;

        this.urls = urls;
        this.urlsTitles = urlsTitles;

        this.depthNumber = depthNumber;
    }

    @Override
    public void run() {
        int repeatTimes = depthNumber;
        do {
            final String currUrl = tasks.poll();
            if (currUrl == null
                    || processedUrls.contains(currUrl)) {
                return;
            }

            final String parsedHtmlCode = parseHtmlCode(currUrl);
            final List<String> parsedLinks = parseLinksFromHtmlCode(currUrl, parsedHtmlCode);
            for (String link : parsedLinks) {
                addUrlToUrlsIfIsNotAdded(link);
                if (!processedUrls.contains(link)) {
                    tasks.offer(link);
                }
            }

            addUrlToUrlsIfIsNotAdded(currUrl);
            processedUrls.add(currUrl);

            repeatTimes--;
        } while (!tasks.isEmpty()
                && repeatTimes > 0);
    }

    private void addUrlToUrlsIfIsNotAdded(final String url) {
        String urlTitle;
        if (!urls.contains(url)) {
            urlTitle = parseTitle(url);
            urls.add(url);
            urlsTitles.add(urlTitle);
        }
    }
}
