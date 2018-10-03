package com.demo.weblinkscraper.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Class to validate the WebLinkURL input.
 * @author Nick Walter
 */
public class WebLinkValidator {

    /**
     * Do some basic URL validation on the input
     * @param inputURL the URL provided
     * @throws IllegalArgumentException if the validation fails
     */
    public static void validateInputURL(final String inputURL) throws IllegalArgumentException {

        if( StringUtils.isBlank(inputURL) ) {
            throw new IllegalArgumentException("InputURL cannot be null or blank");

        } else if( !UrlValidator.getInstance().isValid(inputURL) ) {
            throw new IllegalArgumentException("InputURL is not a valid URL");

        }
    }
}
