package com.company;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawling {

    public static String parseWebpageHtmlCode(final String webpageUrl) {
        String siteText = "";

        try (InputStream inputStream =
                     new BufferedInputStream(
                             new URL(webpageUrl).openStream())) {

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

    public static List<String> parseWebpageUrls(final String webpageUrl) {
        List<String> urls = new ArrayList<String>();

        Pattern patternLinkTag = Pattern.compile("(?i)(<a.*?href=[\"'])(.*?)([\"'].*?>)(.*?)(</a>)");
        Matcher matcherWebpageHtmlCode = patternLinkTag.matcher(
                parseWebpageHtmlCode(webpageUrl)
        );

        String webpageUrlProtocol = webpageUrl.substring(0, webpageUrl.indexOf("//"));

        Matcher matcherWebpageUrlBase = Pattern.compile("^.+/").matcher(webpageUrl);
        String webpageUrlBase = "";
        if (matcherWebpageUrlBase.find()) {
            webpageUrlBase = matcherWebpageUrlBase.group();
        }

        StringBuilder parsedUrl;
        while (matcherWebpageHtmlCode.find()) {
            parsedUrl = new StringBuilder().append(
                    matcherWebpageHtmlCode.group(2)
            );

            if (parsedUrl.toString().startsWith("#")) {
                continue;
            } else if (!parsedUrl.toString().startsWith("http")) {
                if (parsedUrl.toString().startsWith("//")) {
                    parsedUrl.insert(0, webpageUrlProtocol);
                } else if (parsedUrl.toString().startsWith("/")) {
                    parsedUrl.insert(0, webpageUrl);
                } else {
                    parsedUrl.insert(0, webpageUrlBase);
        }
            }

            urls.add(parsedUrl.toString());
        }

        return urls;
    }

}
