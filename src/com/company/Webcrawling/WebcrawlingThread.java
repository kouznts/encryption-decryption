package com.company.Webcrawling;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static com.company.Webcrawling.WebParsing.*;

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

        this.tasks = tasks;
        this.processedUrls = processedUrls;

        this.urls = urls;
        this.urlsTitles = urlsTitles;

        this.depthNumber = depthNumber;
    }

    @Override
    public void run() {
        do {
            final String currUrl = tasks.poll();
            if (cannotRun(currUrl)) {
                return;
            }

            final String parsedHtmlCode = parseHtmlCode(currUrl);
            final String currUrlTitle = parseTitleFromHtmlCode(parsedHtmlCode);

            final List<String> parsedLinks = parseLinksFromHtmlCode(currUrl, parsedHtmlCode);
            String parsedLinkTitle;
            for (String parsedLink : parsedLinks) {
                if (!urls.contains(parsedLink)) {
                    parsedLinkTitle = parseTitle(parsedLink);
                    urls.add(parsedLink);
                    urlsTitles.add(parsedLinkTitle);
                }
            }

            addCurrUrlToUrlsIfIsNotAdded(currUrl, currUrlTitle);
            processedUrls.add(currUrl);
        } while (!tasks.isEmpty());
    }

    private boolean cannotRun(final String url) {
        return url == null
                || processedUrls.contains(url);
    }

    private void addCurrUrlToUrlsIfIsNotAdded(final String url, final String urlTitle) {
        if (isNotAddedToUrls(url)) {
            urls.add(url);
            urlsTitles.add(urlTitle);
        }
    }

    private boolean isNotAddedToUrls(final String url) {
        return !urls.contains(url);
    }

}
