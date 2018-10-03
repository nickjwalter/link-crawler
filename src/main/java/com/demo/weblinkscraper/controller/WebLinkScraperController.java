package com.demo.weblinkscraper.controller;

import java.util.stream.Collectors;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.weblinkscraper.exception.UnableToConnectException;
import com.demo.weblinkscraper.model.WebLinkScraperResult;
import com.demo.weblinkscraper.serviceactivator.HTMLService;

import static com.demo.weblinkscraper.validator.WebLinkValidator.validateInputURL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * REST Controller for accessing the WebLinkScraper service.
 * @author Nick Walter
 */
@Controller
@RequestMapping("/links")
public class WebLinkScraperController {

    @Autowired
    private HTMLService htmlService;

    /**
     * Get the web links from the URL provided.
     * @param inputURL the URL to call and get links from, mandatory
     * @return A populated WebLinkScraperResult
     */
    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody WebLinkScraperResult getWebLinks(@RequestParam(value="inputURL", required=true) String inputURL) throws UnableToConnectException {

        validateInputURL(inputURL);

        final Elements links = htmlService.getHTML(inputURL).select("a[href]");

        return populateWebLinkScraperResult(links);
    }

    /**
     * From the link elements, populate a WebLinkScraperResult with only unique links
     * @param links the HTML link elements
     * @return a populated result object
     */
    private WebLinkScraperResult populateWebLinkScraperResult(Elements links) {
        final WebLinkScraperResult result = new WebLinkScraperResult();

        if (links != null) {

            result.getLinks().addAll(
                links.parallelStream()
                    .map(link -> link.attr("abs:href"))
                    .filter(link -> isNotBlank(link))
                    .collect(Collectors.toSet())
            );
        }

        return result;
    }
}
