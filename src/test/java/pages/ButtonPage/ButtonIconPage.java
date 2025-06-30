package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ButtonIconPage extends BasePage {

    private static final String ROUTE = "buttonicon";

    private final Locator notificationButton;
    private final Locator searchButton;
    private final Locator webforJButton;
    private final Locator notificationIcon;
    private final Locator searchIcon;

    public ButtonIconPage(Page page) {
        super(page);

        notificationButton = page.locator("dwc-button[dwc-id='11']");
        searchButton = page.locator("dwc-button[dwc-id='13']");
        webforJButton = page.locator("dwc-button[dwc-id='15']");
        notificationIcon = page.locator("dwc-icon[dwc-id='12']");
        searchIcon = page.locator("dwc-icon[dwc-id='14']");

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