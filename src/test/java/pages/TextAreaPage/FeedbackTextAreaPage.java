package pages.TextAreaPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class FeedbackTextAreaPage extends BasePage {

    private static final String ROUTE = RouteConfig.TEXT_AREA;

    // Feedback TextArea Elements
    private final Locator mainLabel;
    private final Locator feedbackArea;
    private final Locator charactersCount;
    private final Locator submitButton;
    private final Locator donationToaster;

    public FeedbackTextAreaPage(Page page) {
        super(page);

        // Feedback TextArea (elements from FeedbackTextAreaIT)
        mainLabel = page.locator("dwc-textarea[dwc-id='12'] >> label");
        feedbackArea = page.locator("dwc-textarea[dwc-id='12'] >> textarea");
        charactersCount = page.locator("div[dwc-id='13'] > p[dwc-id='14']");
        submitButton = page.locator("div[dwc-id='13'] > dwc-button[dwc-id='15']");
        donationToaster = page.locator("dwc-toast-group");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Feedback TextArea Getters
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