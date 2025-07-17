package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class TableEditDataViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_EDIT_DATA;

    private final Locator editButton;
    private final Locator input;
    private final Locator saveButton;
    private final Locator title;

    public TableEditDataViewPage(Page page) {
        super(page);

        editButton = page.locator("dwc-icon-button[name='pencil-pin'] >> button").first();
        input = page.locator("#field-1");
        saveButton = page.locator("dwc-button:has-text('Save')");
        title = page.locator("tr td").nth(1).first();

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEditButton() {
        return editButton;
    }

    public Locator getInput() {
        return input;
    }

    public Locator getSaveButton() {
        return saveButton;
    }

    public Locator getTitle() {
        return title;
    }


}