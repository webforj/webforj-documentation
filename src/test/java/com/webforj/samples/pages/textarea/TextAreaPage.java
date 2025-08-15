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
    private final Locator textAreaHost;

    public TextAreaPage(Page page) {
        super(page);

        this.textAreaHost = page.locator("dwc-textarea");

        this.mainLabel = textAreaHost.locator("label");
        this.feedbackArea = textAreaHost.locator("[part~='input']");
        this.charactersCount = textAreaHost.locator("xpath=following-sibling::div[1]/p");
        this.submitButton = page.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Submit Feedback"))
                .locator("[part~='control']");
        this.donationToaster = page.locator("dwc-toast-group").locator("dwc-toast");
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