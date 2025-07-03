package pages.ProgressBarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class ProgressBarDeterminatePage extends BasePage {

    private static final String ROUTE = RouteConfig.PROGRESS_BAR_DETERMINATE;

    private final Locator determinateProgressBar;

    public ProgressBarDeterminatePage(Page page) {
        super(page);

        determinateProgressBar = page.locator("dwc-progressbar[style='--_dwc-progressbar-percent: 0%;']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDeterminateProgressBar() {
        return determinateProgressBar;
    }
}