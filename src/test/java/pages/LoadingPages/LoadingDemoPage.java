package pages.LoadingPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class LoadingDemoPage extends BasePage{

    private static final String LOADING_DEMO = RouteConfig.LOADING_DEMO;

    private final Locator loadingComponent;
    private final Locator spinnerText;
    private final Locator bookIcon;
    private final Locator youtubeIcon;

    public LoadingDemoPage(Page page){
        super(page);

        loadingComponent = page.locator("dwc-spinner[theme='primary']");
        spinnerText = page.locator("focus-trap");
        bookIcon = page.locator("dwc-icon.book");
        youtubeIcon = page.locator("dwc-icon.youtube");
    }

    public static String getRoute() {
        return LOADING_DEMO;
    }

    public Locator getLoadingComponent() {
        return loadingComponent;
    }

    public Locator getSpinnerText() {
        return spinnerText;
    }

    public Locator getBookIcon() {
        return bookIcon;
    }

    public Locator getYoutubeIcon() {
        return youtubeIcon;
    }
}
