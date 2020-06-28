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

    private final String[] tableHeaders = {"URL", "Title"};
    private final List<String> urls;
    private final List<String> urlsTitles;

    private int crawlingThreadsNumber = 1;
    private int depthNumber = 1;

    public Webcrawling() {
        super();
        urls = new CopyOnWriteArrayList<>();
        urlsTitles = new CopyOnWriteArrayList<>();
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

    public int getCrawlingThreadsNumber() {
        return crawlingThreadsNumber;
    }

    public void setCrawlingThreadsNumber(int value) {
        if (value <= 0 || value > 8) {
            throw new IndexOutOfBoundsException();
        }

        crawlingThreadsNumber = value;
    }

    public int getDepthNumber() {
        return depthNumber;
    }

    public void setDepthNumber(int value) {
        if (value <= 0 || value > 4) {
            throw new IndexOutOfBoundsException();
        }

        depthNumber = value;
    }

    public void run(final @NotNull String startUrl) {
        clear();

        Queue<String> processingQueue = new ConcurrentLinkedQueue<>();
        processingQueue.offer(startUrl);
        Set<String> processedUrls = new CopyOnWriteArraySet<>();

        for (int i = 0; i < depthNumber; i++) {
            createAndStartCrawlingThreads(processingQueue, processedUrls);
            processingQueue.clear();
            processingQueue.addAll(urls);
        }

        //removeUrlsWithoutTitles();
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
                thread.join();
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }

    private void removeUrlsWithoutTitles() {
        List<Integer> removingUrlsIndexes = new ArrayList<>();

        for (int i = 0; i < urls.size(); ++i) {
            if (urlsTitles.get(i).equals("")) {
                removingUrlsIndexes.add(i);
            }
        }

        for (int i = 0, deletingIndex; i < removingUrlsIndexes.size(); ++i) {
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
