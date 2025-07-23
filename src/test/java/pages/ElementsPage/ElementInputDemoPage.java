package pages.ElementsPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class ElementInputDemoPage extends BasePage {

    private static final String ROUTE = RouteConfig.ELEMENT_INPUT_DEMO;

    private final Locator input;

    public ElementInputDemoPage(Page page) {
        super(page);

        input = page.locator("input.element--input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInput() {
        return input;
    }
}