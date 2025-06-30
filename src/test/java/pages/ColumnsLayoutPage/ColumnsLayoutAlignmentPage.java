package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ColumnsLayoutAlignmentPage extends BasePage {

    private static final String ROUTE = "columnslayoutalignment";

    private final Locator dwcColumnsLayout;
    private final Locator submitButton;
    
    public ColumnsLayoutAlignmentPage(Page page) {
        super(page);
        this.dwcColumnsLayout = page.locator("dwc-columns-layout");
        this.submitButton = page.locator("dwc-button[dwc-id='19']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcColumnsLayout() {
        return dwcColumnsLayout;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }
} 