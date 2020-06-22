package com.company;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawling {

    public static String downloadWebpage(final String url) {
        String siteText = "";

        try (InputStream inputStream =
                     new BufferedInputStream(
                             new URL(url).openStream())) {

            siteText =
                    new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return siteText;
    }

    public static String parseWebpageTitle(String webpageHtmlCode) {
        Pattern javaPattern = Pattern.compile("(.*?<title>)(.*?)(</title>.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = javaPattern.matcher(webpageHtmlCode);

        String webpageTitle = "";
        if (matcher.find()) {
            webpageTitle = matcher.group(2);
        }

        return webpageTitle;
    }
}
