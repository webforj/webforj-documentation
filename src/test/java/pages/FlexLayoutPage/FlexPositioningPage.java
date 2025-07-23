package pages.FlexLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class FlexPositioningPage extends BasePage {

    private static final String ROUTE = RouteConfig.FLEX_POSITIONING;

    private final Locator flexPositioningContainer;
    private final Locator flexPositioningDropdown;
    private final Locator listBox;

    public FlexPositioningPage(Page page) {
        super(page);

        flexPositioningContainer = page.locator(".button__container--single-row");
        flexPositioningDropdown = page.locator("dwc-button:has-text('Flex-start')");
        listBox = page.locator("dwc-listbox >> li");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFlexPositioningContainer() {
        return flexPositioningContainer;
    }

    public Locator getFlexPositioningDropdown() {
        return flexPositioningDropdown;
    }

    public Locator getListBox(String text) {
        return listBox.locator("text=\"" + text + "\"");
    }
}