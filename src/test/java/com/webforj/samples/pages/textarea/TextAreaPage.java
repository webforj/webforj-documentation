package com.webforj.samples.pages.textarea;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TextAreaPage extends BasePage {

    private static final String ROUTE = "textarea";

    private final Locator mainLabel;
    private final Locator feedbackArea;
    private final Locator charactersCount;
    private final Locator submitButton;
    private final Locator donationToaster;

    public TextAreaPage(Page page) {
        super(page);

        mainLabel = page.locator("dwc-textarea >> label");
        feedbackArea = page.locator("dwc-textarea >> textarea");
        charactersCount = page.locator("div[style*='place-content: center'] > p");
        submitButton = page.locator("dwc-button:has-text('Submit Feedback')");
        donationToaster = page.locator("dwc-toast-group");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMainLabel() {
        return mainLabel;
    }

    public Locator getFeedbackArea() {
        return feedbackArea;
    }

    public Locator getCharactersCount() {
        return charactersCount;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getDonationToaster() {
        return donationToaster;
    }
}