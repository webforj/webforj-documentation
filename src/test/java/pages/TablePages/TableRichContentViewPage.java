package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class TableRichContentViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_RICH_CONTENT;

    private final Locator masterCheckBox;
    private final Locator firstCheckbox;
    private final Locator checkboxInput;
    private final Locator images;

    public TableRichContentViewPage(Page page) {
        super(page);

        masterCheckBox = page.locator("thead input[type='checkbox']");
        firstCheckbox = page.locator("dwc-checkbox").first();
        checkboxInput = firstCheckbox.locator("input[type='checkbox']");
        images = page.locator("img[part='avatar-img']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMasterCheckBox() {
        return masterCheckBox;
    }


    public Locator getFirstCheckbox() {
        return firstCheckbox;
    }

    public Locator getCheckboxInput() {
        return checkboxInput;
    }

    public Locator getImages() {
        return images;
    }

}