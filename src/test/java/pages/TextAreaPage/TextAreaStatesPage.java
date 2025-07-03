package pages.TextAreaPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class TextAreaStatesPage extends BasePage {

    private static final String ROUTE = RouteConfig.TEXT_AREA_STATES;

    // TextArea States Elements
    private final Locator readOnlyArea;
    private final Locator disabledArea;

    public TextAreaStatesPage(Page page) {
        super(page);

        // TextArea States (elements from TextAreaStatesIT)
        readOnlyArea = page.locator("dwc-textarea[dwc-id='12']");
        disabledArea = page.locator("dwc-textarea[dwc-id='14'] >> textarea");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // TextArea States Getters
    public Locator getReadOnlyArea() {
        return readOnlyArea;
    }

    public Locator getDisabledArea() {
        return disabledArea;
    }
}