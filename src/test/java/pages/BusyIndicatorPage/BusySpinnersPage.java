package pages.BusyIndicatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

/**
 * Page Object for Busy Spinners functionality
 */
public class BusySpinnersPage extends BasePage {

    private static final String ROUTE = "busyspinners";

    // BusySpinners locators
    private final Locator busySpinner;

    public BusySpinnersPage(Page page) {
        super(page);
        
        // Initialize BusySpinners locators
        busySpinner = page.locator("dwc-loading[dwc-id='10']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // BusySpinners getters
    public Locator getBusySpinner() {
        return busySpinner;
    }
} 