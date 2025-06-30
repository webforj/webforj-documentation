package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class NumberFieldPage extends BasePage {

    private final Locator numberField;

    public NumberFieldPage(Page page) {
        super(page);
        numberField = page.locator("dwc-field[dwc-id='11'] input");
    }

    public Locator getNumberField() {
        return numberField;
    }
} 