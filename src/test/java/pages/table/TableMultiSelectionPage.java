package pages.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import pages.BasePage;

public class TableMultiSelectionPage extends BasePage {

    private static final String ROUTE = "tablemultiselection";

    private final Locator masterCheckbox;
    private final Locator checkboxes;
    private final Locator headerText;
    private final Locator recordItems;
    private final Locator okButton;
    private final Locator noRecordsMessage;

    public TableMultiSelectionPage(Page page) {
        super(page);
        pageTitle = "Table Multiple Selection";

        masterCheckbox = page.locator("#checkbox-1");
        checkboxes = page.locator("dwc-checkbox input[type='checkbox']");
        headerText = page.locator("dwc-dialog header[slot='header']");
        recordItems = page.locator("dwc-dialog ul li");
        okButton = page.getByRole(AriaRole.CONTENTINFO).getByText("OK");
        noRecordsMessage = page.locator("dwc-dialog >> text=There are no records selected");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public void verifyRecordItems(String text, boolean shouldBeVisible) {
        Locator recordItem = page.locator("dwc-dialog ul li:has-text('" + text + "')");

        if (shouldBeVisible) {
            assertThat(recordItem).isVisible();
        } else {
            assertThat(recordItem).not().isVisible();
        }
    }

    public void verifyRecordItems(String text) {
        verifyRecordItems(text, true);
    }

    public Locator getMasterCheckbox() {
        return masterCheckbox;
    }

    public Locator getCheckboxes() {
        return checkboxes;
    }

    public Locator getHeaderText() {
        return headerText;
    }

    public Locator getRecordItems() {
        return recordItems;
    }

    public Locator getOkButton() {
        return okButton;
    }

    public Locator getNoRecordsMessage() {
        return noRecordsMessage;
    }
}