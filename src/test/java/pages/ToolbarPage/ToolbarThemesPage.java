package pages.ToolbarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToolbarThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOOLBAR_THEMES;

    // Toolbar Themes Elements
    private final Locator dangerToolbar;
    private final Locator defaultToolbar;
    private final Locator grayToolbar;
    private final Locator infoToolbar;
    private final Locator primaryToolbar;
    private final Locator successToolbar;
    private final Locator warningToolbar;

    public ToolbarThemesPage(Page page) {
        super(page);

        // Toolbar Themes (dwc-id from ToolbarThemesIT)
        dangerToolbar = page.locator("dwc-toolbar[dwc-id='11']");
        defaultToolbar = page.locator("dwc-toolbar[dwc-id='16']");
        grayToolbar = page.locator("dwc-toolbar[dwc-id='21']");
        infoToolbar = page.locator("dwc-toolbar[dwc-id='26']");
        primaryToolbar = page.locator("dwc-toolbar[dwc-id='31']");
        successToolbar = page.locator("dwc-toolbar[dwc-id='36']");
        warningToolbar = page.locator("dwc-toolbar[dwc-id='41']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toolbar Themes Getters
    public Locator getDangerToolbar() {
        return dangerToolbar;
    }

    public Locator getDefaultToolbar() {
        return defaultToolbar;
    }

    public Locator getGrayToolbar() {
        return grayToolbar;
    }

    public Locator getInfoToolbar() {
        return infoToolbar;
    }

    public Locator getPrimaryToolbar() {
        return primaryToolbar;
    }

    public Locator getSuccessToolbar() {
        return successToolbar;
    }

    public Locator getWarningToolbar() {
        return warningToolbar;
    }
}