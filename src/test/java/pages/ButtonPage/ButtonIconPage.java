package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ButtonIconPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUTTON_ICON;

    private final Locator notificationButton;
    private final Locator searchButton;
    private final Locator webforJButton;
    private final Locator notificationIcon;
    private final Locator searchIcon;

    public ButtonIconPage(Page page) {
        super(page);

        notificationButton = page.locator("dwc-button:has-text('Notifications')");
        searchButton = page.locator("dwc-button:has-text('Search')");
        webforJButton = page.locator("dwc-button >> img");
        notificationIcon = page.locator(".icon-tabler-bell");
        searchIcon = page.locator(".icon-tabler-external-link");

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNotificationButton() {
        return notificationButton;
    }

    public Locator getSearchButton() {
        return searchButton;
    }

    public Locator getWebforJButton() {
        return webforJButton;
    }

    public Locator getNotificationIcon() {
        return notificationIcon;
    }

    public Locator getSearchIcon() {
        return searchIcon;
    }
}