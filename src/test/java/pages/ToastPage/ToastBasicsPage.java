package pages.ToastPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToastBasicsPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOAST_BASIC;

    // Toast Basics Elements
    private final Locator basicToast;
    private final Locator spinner;
    private final Locator basicMessage;
    private final Locator basicButton;

    /**
     * Constructor for ToastBasicsPage.
     *
     * @param page the Playwright Page object
     */
    public ToastBasicsPage(Page page) {
        super(page);

        // Toast Basics (dwc-id from ToastBasicsTestIT)
        basicToast = page.locator("dwc-toast[dwc-id='10']");
        spinner = basicToast.locator("dwc-spinner");
        basicMessage = basicToast.locator("p[dwc-id='12']:has-text('System update failed')");
        basicButton = basicToast.locator("dwc-button[dwc-id=\"13\"]");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toast Basics Getters
    public Locator getBasicToast() {
        return basicToast;
    }

    public Locator getSpinner() {
        return spinner;
    }

    public Locator getBasicMessage() {
        return basicMessage;
    }

    public Locator getBasicButton() {
        return basicButton;
    }
}