package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ButtonThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUTTON_THEMES;

    private final Locator dangerThemeButton;
    private final Locator outlinedWarningThemeButton;

    public ButtonThemesPage(Page page) {
        super(page);

        dangerThemeButton = page.locator("dwc-button[theme='danger'] >> button");
        outlinedWarningThemeButton = page.locator("dwc-button[theme='outlined-warning'] >> button");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDangerThemeButton() {
        return dangerThemeButton;
    }

    public Locator getOutlinedWarningThemeButton() {
        return outlinedWarningThemeButton;
    }
}