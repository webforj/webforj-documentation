package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DateFieldPage extends BasePage {

    private final Locator departureInput;
    private final Locator returnInput;

    public DateFieldPage(Page page) {
        super(page);
        departureInput = page.locator("dwc-field[dwc-id='11']");
        returnInput = page.locator("dwc-field[dwc-id='12']");
    }

    public Locator getDepartureInput() {
        return departureInput;
    }

    public Locator getReturnInput() {
        return returnInput;
    }
} 