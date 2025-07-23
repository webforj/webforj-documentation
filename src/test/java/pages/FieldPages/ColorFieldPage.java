package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class ColorFieldPage extends BasePage {

    private static final String ROUTE = RouteConfig.COLOR_FIELD;

    private final Locator colorField;
    private final Locator colorBlocks;

    public ColorFieldPage(Page page) {
        super(page);
        colorField = page.locator("dwc-field:has-text('Choose a color:') >> input");
        colorBlocks = page.locator(".colorDiv");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getColorField() {
        return colorField;
    }

    public Locator getColorBlocks() {
        return colorBlocks;
    }
}