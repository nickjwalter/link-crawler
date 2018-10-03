package com.demo.weblinkscraper.serviceactivator;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.demo.weblinkscraper.exception.UnableToConnectException;

/**
 * Class to access a URL and get the HTML as a Jsoup Document.
 * @author Nick Walter
 */
@Profile("default")
@Component
public class HTMLService {

    /**
     * Access the URL and get the HTML
     * @param webLinkURL the web link
     * @return the HTML Page
     */
    public Document getHTML(final String webLinkURL) throws UnableToConnectException {

        Document webDocument = null;
        try {

            webDocument = Jsoup.connect(webLinkURL).get();

        } catch (IOException e) {
            throw new UnableToConnectException("The URL cannot be reached, it may not exist or the connection has timed-out");
        }

        return webDocument;
    }
}
