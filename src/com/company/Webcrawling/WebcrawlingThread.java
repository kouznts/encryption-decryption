package com.company.Webcrawling;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static com.company.Webcrawling.WebParsing.*;

public class WebcrawlingThread extends Thread {
    private final Queue<String> processingUrls;
    private final Set<String> processedUrls;

    private final List<String> urls;
    private final List<String> urlsTitles;

    private final int depthNumber;

    public WebcrawlingThread(
            @NotNull final List<String> urls, @NotNull final List<String> urlsTitles,
            @NotNull final Queue<String> processingQueue, @NotNull final Set<String> processedUrls,
            final int depthNumber) {

        this.processingUrls = processingQueue;
        this.processedUrls = processedUrls;

        this.urls = urls;
        this.urlsTitles = urlsTitles;

        this.depthNumber = depthNumber;
    }

    @Override
    public void run() {
        do {
            final String currUrl = processingUrls.poll();
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

            addCurrUrlIfIsNotAdded(currUrl, currUrlTitle);
            processedUrls.add(currUrl);
        } while (!processingUrls.isEmpty());
    }

    private boolean cannotRun(final String currUrl) {
        return currUrl == null
                || processedUrls.contains(currUrl);
    }

    private void addCurrUrlIfIsNotAdded(final String currUrl, final String currUrlTitle) {
        if (isNotAdded(currUrl)) {
            urls.add(currUrl);
            urlsTitles.add(currUrlTitle);
        }
    }

    private boolean isNotAdded(final String currUrl) {
        return !urls.contains(currUrl);
    }
}
