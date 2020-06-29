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

    public WebcrawlingThread(
            @NotNull final List<String> urls, @NotNull final List<String> urlsTitles,
            @NotNull final Queue<String> processingQueue, @NotNull final Set<String> processedUrls) {

        this.processingUrls = processingQueue;
        this.processedUrls = processedUrls;

        this.urls = urls;
        this.urlsTitles = urlsTitles;
    }

    @Override
    public void run() {
        do {
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
                String parsedLinkTitle;
                for (String parsedLink : parsedLinks) {
                    if (!urls.contains(parsedLink)) {
                        parsedLinkTitle = parseTitle(parsedLink);
                        urls.add(parsedLink);
                        urlsTitles.add(parsedLinkTitle);
                    }
                }

                processedUrls.add(currUrl);
            }
        } while (!processingUrls.isEmpty());
    }
}
