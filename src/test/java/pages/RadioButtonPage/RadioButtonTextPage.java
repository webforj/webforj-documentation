package pages.RadioButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class RadioButtonTextPage extends BasePage {

    private static final String ROUTE = RouteConfig.RADIO_BUTTON_TEXT;

    private final Locator rightAlignedInput;
    private final Locator leftAlignedInput;

    public RadioButtonTextPage(Page page) {
        super(page);

        rightAlignedInput = page.locator("dwc-radio:has-text('Right aligned') >> input");
        leftAlignedInput = page.locator("dwc-radio:has-text('Left aligned') >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getRightAlignedInput() {
        return rightAlignedInput;
    }

    public Locator getLeftAlignedInput() {
        return leftAlignedInput;
    }
}