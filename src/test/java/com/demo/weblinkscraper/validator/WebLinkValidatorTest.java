package com.demo.weblinkscraper.validator;

import org.junit.Test;

/**
 * Unit tests for the WebLinkValidator class.
 * @author Nick Walter
 */
public class WebLinkValidatorTest {

    @Test
    public void whenAValidURLIsProvided() throws Exception {
        // given
        String testURL = "https://www.bbc.co.uk/sport";

        // when
        WebLinkValidator.validateInputURL(testURL);

        // expect no exception
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenANullURLIsProvided() throws Exception {
        // given
        String testURL = null;

        // when
        WebLinkValidator.validateInputURL(testURL);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenABlankURLIsProvided() throws Exception {
        // given
        String testURL = "";

        // when
        WebLinkValidator.validateInputURL(testURL);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenAWhiteSpaceURLIsProvided() throws Exception {
        // given
        String testURL = "       ";

        // when
        WebLinkValidator.validateInputURL(testURL);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenAnInvalidURLIsProvided() throws Exception {
        // given
        String testURL = "not.valid@url!.../example.com/test ";

        // when
        WebLinkValidator.validateInputURL(testURL);
    }

}