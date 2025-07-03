package pages.ToolbarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToolbarProgressBarPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOOLBAR_PROGRESS_BAR;

    // Toolbar Progress Bar Elements
    private final Locator progressBarToolbar;
    private final Locator progressBar;

    public ToolbarProgressBarPage(Page page) {
        super(page);

        // Toolbar Progress Bar (dwc-id from ToolbarProgressBarIT)
        progressBarToolbar = page.locator("dwc-toolbar[dwc-id='13']");
        progressBar = progressBarToolbar.locator("dwc-progressbar[dwc-id='16']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toolbar Progress Bar Getters
    public Locator getProgressBarToolbar() {
        return progressBarToolbar;
    }

    public Locator getProgressBar() {
        return progressBar;
    }
}