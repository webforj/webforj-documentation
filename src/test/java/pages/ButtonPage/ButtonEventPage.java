package pages.ButtonPage;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class ButtonEventPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUTTON_EVENT;

    private final Locator button;
    private final Locator counter;

    public ButtonEventPage(Page page) {
        super(page);

        button = page.locator("dwc-button:has-text('Click Me!')");
        counter = page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Current Counter: \\d+$")));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getButton() {
        return button;
    }

    public Locator getCounter() {
        return counter;
    }
}
