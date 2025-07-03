package pages.CheckboxPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class CheckboxExpansePage extends BasePage {

    private static final String ROUTE = RouteConfig.CHECKBOX_EXPANSE;

    private final Locator noneCheckbox;
    private final Locator xsmallCheckbox;
    private final Locator smallCheckbox;
    private final Locator mediumCheckbox;
    private final Locator largeCheckbox;
    private final Locator xlargeCheckbox;

    public CheckboxExpansePage(Page page) {
        super(page);

        noneCheckbox = page.locator("dwc-checkbox[expanse='']");
        xsmallCheckbox = page.locator("dwc-checkbox[expanse='xs']");
        smallCheckbox = page.locator("dwc-checkbox[expanse='s']");
        mediumCheckbox = page.locator("dwc-checkbox[expanse='m']");
        largeCheckbox = page.locator("dwc-checkbox[expanse='l']");
        xlargeCheckbox = page.locator("dwc-checkbox[expanse='xl']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNoneExpanseCheckbox() {
        return noneCheckbox;
    }

    public Locator getXSmallExpanseCheckbox() {
        return xsmallCheckbox;
    }

    public Locator getSmallExpanseCheckbox() {
        return smallCheckbox;
    }

    public Locator getMediumExpanseCheckbox() {
        return mediumCheckbox;
    }

    public Locator getLargeExpanseCheckbox() {
        return largeCheckbox;
    }

    public Locator getXLargeExpanseCheckbox() {
        return xlargeCheckbox;
    }
}