package pages.textarea;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TextAreaPredictedTextPage extends BasePage {

    private static final String ROUTE = "textareapredictedtext";

    // Predicted Text Demo Elements
    private final Locator predictedTextArea;

    public TextAreaPredictedTextPage(Page page) {
        super(page);

        // Predicted Text Demo (elements from PredictedTextDemoIT)
        predictedTextArea = page.locator("dwc-textarea:has-text('Predicted Text') >> textarea");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Predicted Text Demo Getters
    public Locator getPredictedTextArea() {
        return predictedTextArea;
    }
}