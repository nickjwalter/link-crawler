package com.demo.weblinkscraper.controller;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.weblinkscraper.exception.UnableToConnectException;
import com.demo.weblinkscraper.serviceactivator.HTMLService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration Test using a Mock HTMLService.
 * @author Nick Walter
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WebLinkScraperController.class)
public class WebLinkScraperControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HTMLService mockHTMLService;

    private Document testHTMLPage;
    private Document blankHTMLPage;
    private Document noLinkHTMLPage;
    private Document invalidHTMLPage;

    @Before
    public void setup() throws Exception {
        reset(mockHTMLService);

        populateTestDocuments();
    }

    @Test
    public void whenResultHasLinks() throws Exception {
        // given
        final String testURL = "https://www.testurl.com/test";

        when(mockHTMLService.getHTML(testURL)).thenReturn(testHTMLPage);

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.links", hasSize(26)));

    }

    @Test
    public void whenURLWasABlankPage() throws Exception {
        // given
        final String testURL = "https://www.testurl.com/blank";

        when(mockHTMLService.getHTML(testURL)).thenReturn(blankHTMLPage);

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.links", hasSize(0)));

    }

    @Test
    public void whenURLWasAPageWithNoLink() throws Exception {
        // given
        final String testURL = "https://www.testurl.com/nolinks";

        when(mockHTMLService.getHTML(testURL)).thenReturn(noLinkHTMLPage);

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.links", hasSize(0)));

    }

    @Test
    public void whenURLWasAPageWithInvalidHTML() throws Exception {
        // given
        final String testURL = "https://www.testurl.com/invalid";

        when(mockHTMLService.getHTML(testURL)).thenReturn(invalidHTMLPage);

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.links", hasSize(0)));

    }

    @Test
    public void whenTheURLFailsValidation() throws Exception {
        // given
        final String testURL = "this.is@not.valid/url";

        when(mockHTMLService.getHTML(testURL)).thenReturn(testHTMLPage);

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void whenTheURLCannotBeReached() throws Exception {
        // given
        final String testURL = "https://www.doesnot.com/exist";

        when(mockHTMLService.getHTML(testURL)).thenThrow(new UnableToConnectException("Unable to connect"));

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void whenAnUnknownErrorOccurs() throws Exception {
        // given
        final String testURL = "https://www.doesnot.com/exist";

        when(mockHTMLService.getHTML(testURL)).thenThrow(new NullPointerException("Ahh Failed"));

        // when
        mvc.perform(MockMvcRequestBuilders.get("/links?inputURL=" + testURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    /**
     * Load a test html files as a Jsoup document.
     * @throws Exception
     */
    private void populateTestDocuments() throws Exception {
        File testHTML = new File("src/test/resources/html/test.html");
        testHTMLPage = Jsoup.parse(testHTML, "UTF-8", "https://www.testurl.com/test");

        testHTML = new File("src/test/resources/html/blank.html");
        blankHTMLPage = Jsoup.parse(testHTML, "UTF-8", "https://www.testurl.com/blank");

        testHTML = new File("src/test/resources/html/noLinks.html");
        noLinkHTMLPage = Jsoup.parse(testHTML, "UTF-8", "https://www.testurl.com/noLinks");

        testHTML = new File("src/test/resources/html/invalid.html");
        invalidHTMLPage = Jsoup.parse(testHTML, "UTF-8", "https://www.testurl.com/invalid");
    }

}