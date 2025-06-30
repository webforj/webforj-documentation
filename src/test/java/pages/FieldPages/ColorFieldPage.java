package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ColorFieldPage extends BasePage {
    private final Locator colorField;
    private final Locator colorBlocks;

    public ColorFieldPage(Page page) {
        super(page);
        colorField = page.locator("#field-1");
        colorBlocks = page.locator(".colorDiv");
    }

    public Locator getColorField() {
        return colorField;
    }

    public Locator getColorBlocks() {
        return colorBlocks;
    }
} 