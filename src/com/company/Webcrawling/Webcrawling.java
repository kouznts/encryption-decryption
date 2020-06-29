package com.company.Webcrawling;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Webcrawling extends AbstractTableModel {
    public static final int DEFAULT_MILLIS_LIMIT = 20000;

    private final String[] tableHeaders;
    private final List<String> urls;
    private final List<String> urlsTitles;

    private int crawlingThreadsNumber;
    private int depthNumber;
    private long millisLimit;

    public Webcrawling() {
        super();

        tableHeaders = new String[]{"URL", "Title"};
        urls = new CopyOnWriteArrayList<>();
        urlsTitles = new CopyOnWriteArrayList<>();

        crawlingThreadsNumber = 1;
        depthNumber = 1;
        millisLimit = DEFAULT_MILLIS_LIMIT;
    }

    @Override
    public int getRowCount() {
        return urls.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getValueAt(int row, int col) {
        return col == 0
                ? urls.get(row)
                : urlsTitles.get(row);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return tableHeaders[columnIndex];
    }

    public int getSecondsLimit() {
        return (int) millisLimit / 1000;
    }

    public int getParsedPagesNumber() {
        return urls.size();
    }

    public void setCrawlingThreadsNumber(int value) {
        if (value <= 0 || value > 8) {
            throw new IndexOutOfBoundsException();
        }

        crawlingThreadsNumber = value;
    }

    public void setDepthNumber(int value) {
        if (value <= 0 || value > 4) {
            throw new IndexOutOfBoundsException();
        }

        depthNumber = value;
    }

    public void setSecondsLimit(int value) {
        if (value < 5 || value > 60) {
            throw new IndexOutOfBoundsException();
        }

        millisLimit = value * 1000;
    }

    public void resetSecondsLimit() {
        millisLimit = DEFAULT_MILLIS_LIMIT;
    }

    public void run(final @NotNull String startUrl) {
        clear();

        Queue<String> processingQueue = new ConcurrentLinkedQueue<>();
        processingQueue.offer(startUrl);
        Set<String> processedUrls = new CopyOnWriteArraySet<>();

        for (int i = 0; i < depthNumber; i++) {
            createAndStartCrawlingThreads(processingQueue, processedUrls);
            processingQueue.clear();
            addUnprocessedUrlsToProcessingQueue(processingQueue, processedUrls);
        }
    }

    private void clear() {
        urls.clear();
        urlsTitles.clear();
    }

    private void createAndStartCrawlingThreads(
            final @NotNull Queue<String> processingUrls, final @NotNull Set<String> processedUrls) {

        Thread[] threads = new Thread[crawlingThreadsNumber];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new WebcrawlingThread(urls, urlsTitles, processingUrls, processedUrls);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(millisLimit);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }

    private void addUnprocessedUrlsToProcessingQueue(
            final @NotNull Queue<String> processingUrls, final @NotNull Set<String> processedUrls
    ) {
        String currUrl;
        for (String url : urls) {
            currUrl = url;
            if (!processedUrls.contains(currUrl))
                processingUrls.add(currUrl);
        }
    }

    public void removeUrlsWithoutTitles() {
        List<Integer> removingUrlsIndexes = new ArrayList<>();

        for (int i = 0; i < urls.size(); i++) {
            if (urlsTitles.get(i).equals("")) {
                removingUrlsIndexes.add(i);
            }
        }

        for (int i = 0, deletingIndex; i < removingUrlsIndexes.size(); i++) {
            deletingIndex = removingUrlsIndexes.get(i);
            urls.remove(deletingIndex);
            urlsTitles.remove(deletingIndex);
        }
    }

    public void export(final @NotNull String exportFileName) {
        try (PrintWriter fileWriter = new PrintWriter(exportFileName, StandardCharsets.UTF_8)) {
            for (int i = 0; i < urls.size(); i++) {
                fileWriter.println(urls.get(i));
                fileWriter.println(urlsTitles.get(i));
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
