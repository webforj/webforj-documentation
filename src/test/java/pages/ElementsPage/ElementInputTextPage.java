package pages.ElementsPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class ElementInputTextPage extends BasePage {

    private static final String ROUTE = RouteConfig.ELEMENT_INPUT_TEXT;

    private final Locator input;

    public ElementInputTextPage(Page page) {
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