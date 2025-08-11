package com.webforj.samples.pages.textarea;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TextAreaPredictedTextPage extends BasePage {

    private static final String ROUTE = "textareapredictedtext";

    // Predicted Text Demo Elements
    private final Locator predictedTextArea;

    public TextAreaPredictedTextPage(Page page) {
        super(page);

        // Predicted Text Demo (elements from PredictedTextDemoIT)
        // Ensure we target the actual editable textarea, not the readonly predicted
        // overlay
        predictedTextArea = page.locator(
                "dwc-textarea:has-text('Predicted Text') >> textarea[part='input']:not([readonly])");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Predicted Text Demo Getters
    public Locator getPredictedTextArea() {
        return predictedTextArea;
    }
}