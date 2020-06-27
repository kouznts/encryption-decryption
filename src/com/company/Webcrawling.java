package com.company;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.company.WebParsing.parseLinks;
import static com.company.WebParsing.parseUrlsTitles;

public class Webcrawling extends AbstractTableModel {

    private final String[] tableHeaders = {"URL", "Title"};
    private final List<String> urls;
    private final List<String> urlsTitles;

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

    public void run(final @NotNull String startUrl) {
        clear();

        urls.add(startUrl);
        urls.addAll(parseLinks(startUrl));
        urlsTitles.addAll(parseUrlsTitles(urls));

        removeUrlsWithoutTitles();
    }

    private void clear() {
        urls.clear();
        urlsTitles.clear();
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
