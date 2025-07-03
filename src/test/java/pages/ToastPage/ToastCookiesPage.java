package pages.ToastPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToastCookiesPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOAST_COOKIES;

    // Toast Cookies Elements
    private final Locator cookieIcon;
    private final Locator cookieText;
    private final Locator acceptButton;
    private final Locator necessaryButton;

    /**
     * Constructor for ToastCookiesPage.
     *
     * @param page the Playwright Page object
     */
    public ToastCookiesPage(Page page) {
        super(page);

        // Toast Cookies (elements from ToastCookiesIT)
        cookieIcon = page.locator("dwc-icon[name='cookie']");
        cookieText = page.locator("p[dwc-id='12']");
        acceptButton = page.locator("dwc-button[dwc-id='15']");
        necessaryButton = page.locator("dwc-button[dwc-id='16']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toast Cookies Getters
    public Locator getCookieIcon() {
        return cookieIcon;
    }

    public Locator getCookieText() {
        return cookieText;
    }

    public Locator getAcceptButton() {
        return acceptButton;
    }

    public Locator getNecessaryButton() {
        return necessaryButton;
    }
}