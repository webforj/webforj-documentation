package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ColumnsLayoutAlignmentPage extends BasePage {

    private static final String ROUTE = RouteConfig.COLUMNS_LAYOUT_ALIGNMENT;

    private final Locator dwcColumnsLayout;
    private final Locator submitButton;

    public ColumnsLayoutAlignmentPage(Page page) {
        super(page);

        dwcColumnsLayout = page.locator("dwc-columns-layout");
        submitButton = page.locator("dwc-button:has-text('Submit')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcColumnsLayout() {
        return dwcColumnsLayout;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }
}