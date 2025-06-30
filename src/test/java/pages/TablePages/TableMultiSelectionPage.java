package pages.TablePages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TableMultiSelectionPage extends BasePage {
    private final Locator masterCheckbox;
    private final Locator checkboxes;
    private final Locator headerText;
    private final Locator recordItems;
    private final Locator okButton;
    private final Locator noRecordsMessage;

    public TableMultiSelectionPage(Page page) {
        super(page);
        this.pageUrl = "https://docs.webforj.com/tablemultiselection?";
        this.pageTitle = "Table Multiple Selection";

        this.masterCheckbox = page.locator("#checkbox-1");
        this.checkboxes = page.locator("dwc-checkbox input[type='checkbox']");
        this.headerText = page.locator("dwc-dialog header[slot='header']");
        this.recordItems = page.locator("dwc-dialog ul li");
        this.okButton = page.locator("dwc-button >> button[part='control']");
        this.noRecordsMessage = page.locator("dwc-dialog >> text=There are no records selected");
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