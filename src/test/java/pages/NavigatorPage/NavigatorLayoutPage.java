package pages.NavigatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;
import utils.WaitUtil;

public class NavigatorLayoutPage extends BasePage {

    private static final String ROUTE = RouteConfig.NAVIGATOR_LAYOUT;

    private final Locator navigator;
    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;
    private final Locator navigatorValue;

    public NavigatorLayoutPage(Page page) {
        super(page);

        navigator = page.locator("dwc-navigator.nav");
        firstButton = page.locator("button[title='Goto first page']");
        prevButton = page.locator("button[title='Goto previous page']");
        nextButton = page.locator("button[title='Goto next page']");
        lastButton = page.locator("button[title='Goto last page']");
        navigatorValue = page.locator("div[part='area area-middle']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public void waitForVisiblePaginator() {
        WaitUtil.waitForVisible(navigator);
    }

    public Locator getFirstButton() {
        return firstButton;
    }

    public Locator getPrevButton() {
        return prevButton;
    }

    public Locator getNextButton() {
        return nextButton;
    }

    public Locator getLastButton() {
        return lastButton;
    }

    public Locator getNavigatorValue() {
        return navigatorValue;
    }
}