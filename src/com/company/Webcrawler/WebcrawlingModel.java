package com.company.Webcrawler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebcrawlingModel {
    private final List<String> urls;
    private final List<String> urlsTitles;

    public WebcrawlingModel() {
        urls = new CopyOnWriteArrayList<>();
        urlsTitles = new CopyOnWriteArrayList<>();
    }

    public void Clear() {
        urls.clear();
        urlsTitles.clear();
    }
}
