package com.company;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.company.Webcrawling.*;

public class WebcrawlingModel extends AbstractTableModel {

    private final String[] tableHeaders = {"URL", "Title"};
    private final List<String> urls;
    private final List<String> urlsTitles;

    public WebcrawlingModel() {
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

        deleteUrlsWithoutTitles(urls, urlsTitles);
    }

    private void clear() {
        urls.clear();
        urlsTitles.clear();
    }
}
