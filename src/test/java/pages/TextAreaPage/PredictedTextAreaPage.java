package pages.TextAreaPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class PredictedTextAreaPage extends BasePage {

    private static final String ROUTE = RouteConfig.TEXT_AREA_PREDICTED_TEXT;

    // Predicted Text Demo Elements
    private final Locator predictedTextArea;

    public PredictedTextAreaPage(Page page) {
        super(page);

        // Predicted Text Demo (elements from PredictedTextDemoIT)
        predictedTextArea = page.locator("#cedit-1");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Predicted Text Demo Getters
    public Locator getPredictedTextArea() {
        return predictedTextArea;
    }
}