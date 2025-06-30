package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DateTimeFieldPage extends BasePage {

    private final Locator departureInput;

    public DateTimeFieldPage(Page page) {
        super(page);
        departureInput = page.locator("dwc-field[dwc-id='11']");
    }

    public Locator getDepartureInput() {
        return departureInput;
    }
} 