package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ColumnsLayoutSpanPage extends BasePage {

    private static final String ROUTE = "columnslayoutspancolumn";

    private final Locator dwcColumnsLayout;
    private final Locator email;

    public ColumnsLayoutSpanPage(Page page) {
        super(page);
        this.dwcColumnsLayout = page.locator("dwc-columns-layout");
        this.email = page.locator("dwc-field[dwc-id='14']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcColumnsLayout() {
        return dwcColumnsLayout;
    }

    public Locator getEmail() {
        return email;
    }
} 