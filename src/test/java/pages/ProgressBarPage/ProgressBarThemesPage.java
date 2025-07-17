package pages.ProgressBarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class ProgressBarThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.PROGRESS_BAR_THEMES;

    private final Locator dangerBar;
    private final Locator defaultBar;
    private final Locator grayBar;
    private final Locator infoBar;
    private final Locator primaryBar;
    private final Locator successBar;
    private final Locator warningBar;

    public ProgressBarThemesPage(Page page) {
        super(page);

        dangerBar = page.locator("dwc-progressbar[theme='danger']");
        defaultBar = page.locator("dwc-progressbar[theme='default']");
        grayBar = page.locator("dwc-progressbar[theme='gray']");
        infoBar = page.locator("dwc-progressbar[theme='info']");
        primaryBar = page.locator("dwc-progressbar[theme='primary']");
        successBar = page.locator("dwc-progressbar[theme='success']");
        warningBar = page.locator("dwc-progressbar[theme='warning']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDangerBar() {
        return dangerBar;
    }

    public Locator getDefaultBar() {
        return defaultBar;
    }

    public Locator getGrayBar() {
        return grayBar;
    }

    public Locator getInfoBar() {
        return infoBar;
    }

    public Locator getPrimaryBar() {
        return primaryBar;
    }

    public Locator getSuccessBar() {
        return successBar;
    }

    public Locator getWarningBar() {
        return warningBar;
    }
}