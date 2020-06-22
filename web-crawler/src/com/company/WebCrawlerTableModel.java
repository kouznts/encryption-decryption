package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WebCrawlerTableModel extends AbstractTableModel {

    private final String[] tableHeaders = {"URL", "Title"};
    private final List<String> urls;
    private final List<String> titles;

    public WebCrawlerTableModel(List<String> urls, List<String> titles) {
        super();
        this.urls = urls;
        this.titles = titles;
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
        if (row < 0 || col < 0 || col > 1) {
            throw new IndexOutOfBoundsException();
        }

        return col == 0
                ? urls.get(row)
                : titles.get(row);
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex < 0 || columnIndex > 1) {
            throw new IndexOutOfBoundsException();
        }

        return tableHeaders[columnIndex];
    }
}