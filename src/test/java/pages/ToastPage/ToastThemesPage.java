package pages.ToastPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToastThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOAST_THEME;

    // Toast Themes Elements
    private final Locator customThemeToast;
    private final Locator themeMessageText;

    /**
     * Constructor for ToastThemesPage.
     *
     * @param page the Playwright Page object
     */
    public ToastThemesPage(Page page) {
        super(page);

        // Toast Themes (elements from ToastThemesIT)
        customThemeToast = page.locator("dwc-toast[dwc-id=\"10\"]");
        themeMessageText = customThemeToast.locator("div[part='message']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toast Themes Getters
    public Locator getCustomThemeToast() {
        return customThemeToast;
    }

    public Locator getThemeMessageText() {
        return themeMessageText;
    }
}