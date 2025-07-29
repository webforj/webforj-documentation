package pages.fields.numberfield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class NumberFieldPage extends BasePage {

    private static final String ROUTE = "numberfield";

    private final Locator numberField;

    public NumberFieldPage(Page page) {
        super(page);
        numberField = page.locator("dwc-field:has-text('Quantity') >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNumberField() {
        return numberField;
    }
}