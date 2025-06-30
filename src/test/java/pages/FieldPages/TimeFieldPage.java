package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TimeFieldPage extends BasePage {

    private final Locator reminderField;

    public TimeFieldPage(Page page) {
        super(page);
        reminderField = page.locator("dwc-field[dwc-id='11'] input");
    }

    public Locator getReminderField() {
        return reminderField;
    }
} 