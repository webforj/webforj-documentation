package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class TableColumnPinningViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_COLUMN_PINNING;

    private final Locator editButtonPosition;
    private final Locator editButton;
    private final Locator dialogBox;

    public TableColumnPinningViewPage(Page page) {
        super(page);

        editButtonPosition = page.locator("td[part~='cell-pinned-right']").first();
        editButton = page.locator("td dwc-button").first();
        dialogBox = page.locator("dwc-dialog[type='msgbox'] >> section");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEditButtonPosition() {
        return editButtonPosition;
    }

    public Locator getEditButton() {
        return editButton;
    }

    public Locator getDialogBox() {
        return dialogBox;
    }
}