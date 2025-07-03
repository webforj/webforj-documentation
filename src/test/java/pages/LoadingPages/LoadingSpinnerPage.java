package pages.LoadingPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class LoadingSpinnerPage extends BasePage {

    private static final String LOADING_SPINNER = RouteConfig.LOADING_SPINNER_DEMO;

    private final Locator loading;

    public LoadingSpinnerPage(Page page) {
        super(page);

        loading = page.locator("dwc-loading.hydrated");
    }

    public static String getRoute() {
        return LOADING_SPINNER;
    }

    public Locator getLoading() {
        return loading;
    }
}
