package com.webforj.samples.pages.textarea;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class TextAreaPage extends BasePage {

    private static final String ROUTE = "textarea";

    private final Locator feedbackTextarea;
    private final Locator submitButton;
    private final Locator donationToaster;

    public TextAreaPage(Page page) {
        super(page);

        this.feedbackTextarea = page.getByLabel("What do you think about this demo?");
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit Feedback"));
        this.donationToaster = page.getByText("Thank you for your feedback!");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFeedbackTextarea() {
        return feedbackTextarea;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getDonationToaster() {
        return donationToaster;
    }
}