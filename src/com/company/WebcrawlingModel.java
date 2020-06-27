package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public void Clear() {
        urls.clear();
        urlsTitles.clear();
    }
}
