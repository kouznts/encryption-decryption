package com.company.WebCrawling;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static com.company.WebCrawling.WebCrawling.*;

public class WebCrawlingThread extends Thread {
    private final Queue<String> processingUrls;
    private final Set<String> processedUrls;

    private final List<String> urls;
    private final List<String> urlsTitles;

    public WebCrawlingThread(
            @NotNull final Queue<String> processingUrls, @NotNull final Set<String> processedUrls,
            @NotNull final List<String> urls, @NotNull final List<String> urlsTitles) {
        this.processingUrls = processingUrls;
        this.processedUrls = processedUrls;

        this.urls = urls;
        this.urlsTitles = urlsTitles;
    }

    @Override
    public void run() {
        final String currUrl = processingUrls.poll();
        if (currUrl == null) {
            return;
        }
        if (processedUrls.contains(currUrl)) {
            return;
        }

        if (!urls.contains(currUrl)) {
            final String parsedHtmlCode = parseHtmlCode(currUrl);
            final String currUrlTitle = parseTitleFromHtmlCode(parsedHtmlCode);
            urls.add(currUrl);
            urlsTitles.add(currUrlTitle);

            final List<String> parsedLinks = parseLinksFromHtmlCode(currUrl, parsedHtmlCode);
            String parsedLink = "";
            String parsedLinkTitle = "";
            for (String link : parsedLinks) {
                parsedLink = link;
                if (!urls.contains(parsedLink)) {
                    parsedLinkTitle = parseTitle(parsedLink);
                    urls.add(parsedLink);
                    urlsTitles.add(parsedLinkTitle);
                }
            }

            processedUrls.add(currUrl);
        }
    }
}
