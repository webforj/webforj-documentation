package pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class ElementInputTextPage extends BasePage {

    private static final String ROUTE = "elementinputtext";

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