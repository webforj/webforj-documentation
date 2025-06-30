package pages.CheckboxPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class CheckboxExpansePage extends BasePage {

    private static final String ROUTE = "checkboxexpanse";

    private final Locator noneCheckbox;
    private final Locator xsmallCheckbox;
    private final Locator smallCheckbox;
    private final Locator mediumCheckbox;
    private final Locator largeCheckbox;
    private final Locator xlargeCheckbox;

    public CheckboxExpansePage(Page page) {
        super(page);

        noneCheckbox = page.locator("dwc-checkbox[dwc-id='11']");
        xsmallCheckbox = page.locator("dwc-checkbox[dwc-id='12']");
        smallCheckbox = page.locator("dwc-checkbox[dwc-id='13']");
        mediumCheckbox = page.locator("dwc-checkbox[dwc-id='14']");
        largeCheckbox = page.locator("dwc-checkbox[dwc-id='15']");
        xlargeCheckbox = page.locator("dwc-checkbox[dwc-id='16']");
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