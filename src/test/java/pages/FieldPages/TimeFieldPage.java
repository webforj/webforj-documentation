package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class TimeFieldPage extends BasePage {

    private static final String ROUTE = RouteConfig.TIME_FIELD;

    private final Locator reminderField;

    public TimeFieldPage(Page page) {
        super(page);
        reminderField = page.locator("dwc-field:has-text('Set Reminder') >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getReminderField() {
        return reminderField;
    }
}