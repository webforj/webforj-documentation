package pages.CheckboxPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class CheckboxHorizontalTextPage extends BasePage {

    private static final String ROUTE = "checkboxhorizontaltext";

    private final Locator leftAlignedCheckbox;
    private final Locator weeklyCheckboxLabel;
    private final Locator weeklyCheckboxInput;

    public CheckboxHorizontalTextPage(Page page) {
        super(page);

        leftAlignedCheckbox = page.locator("dwc-checkbox[dwc-id='18']");
        weeklyCheckboxLabel = page.locator("dwc-checkbox[dwc-id='19'] >> label[part='label']");
        weeklyCheckboxInput = page.locator("dwc-checkbox[dwc-id='19'] >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getLeftAlignedCheckbox() {
        return leftAlignedCheckbox;
    }

    public Locator getWeeklyCheckboxLabel() {
        return weeklyCheckboxLabel;
    }

    public Locator getWeeklyCheckboxInput() {
        return weeklyCheckboxInput;
    }
} 