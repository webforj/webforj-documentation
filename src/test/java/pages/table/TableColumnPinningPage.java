package pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TableColumnPinningPage extends BasePage {
    private static final String ROUTE = "tablecolumnpinning";

    private final Locator editButtonPosition;
    private final Locator editButton;
    private final Locator dialogBox;

    public TableColumnPinningPage(Page page) {
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