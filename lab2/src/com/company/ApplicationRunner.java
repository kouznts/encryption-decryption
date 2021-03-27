package com.company;

import com.company.WebcrawlerApp.WebcrawlerApp;
import com.company.WebcrawlerApp.WebcrawlerAppSettings;

public class ApplicationRunner {
    public static void main(String[] args) {

        new WebcrawlerApp(
                new WebcrawlerAppSettings()
        );
    }
}
