package pages.SpinnerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SpinnerThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPINNER_THEME_DEMO;

    private final Locator defaultThemeSpinner;
    private final Locator primaryThemeSpinner;
    private final Locator successThemeSpinner;
    private final Locator dangerThemeSpinner;
    private final Locator warningThemeSpinner;
    private final Locator grayThemeSpinner;
    private final Locator infoThemeSpinner;

    public SpinnerThemesPage(Page page) {
        super(page);

        defaultThemeSpinner = page.locator("dwc-spinner[dwc-id='11']");
        primaryThemeSpinner = page.locator("dwc-spinner[dwc-id='12']");
        successThemeSpinner = page.locator("dwc-spinner[dwc-id='13']");
        dangerThemeSpinner = page.locator("dwc-spinner[dwc-id='14']");
        warningThemeSpinner = page.locator("dwc-spinner[dwc-id='15']");
        grayThemeSpinner = page.locator("dwc-spinner[dwc-id='16']");
        infoThemeSpinner = page.locator("dwc-spinner[dwc-id='17']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDefaultThemeSpinner() {
        return defaultThemeSpinner;
    }

    public Locator getPrimaryThemeSpinner() {
        return primaryThemeSpinner;
    }

    public Locator getSuccessThemeSpinner() {
        return successThemeSpinner;
    }

    public Locator getDangerThemeSpinner() {
        return dangerThemeSpinner;
    }

    public Locator getWarningThemeSpinner() {
        return warningThemeSpinner;
    }

    public Locator getGrayThemeSpinner() {
        return grayThemeSpinner;
    }

    public Locator getInfoThemeSpinner() {
        return infoThemeSpinner;
    }
}