package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogSectionsPage extends BasePage {

    private static final String ROUTE = "dialogsectionsview";

    private final Locator header;
    private final Locator content;
    private final Locator footer;


    public DialogSectionsPage(Page page) {
        super(page);
        header = page.locator("div[dwc-id='12']");
        content = page.locator("div[dwc-id='13']");
        footer = page.locator("div[dwc-id='14']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHeader() {
        return header;
    }

    public Locator getContent() {
        return content;
    }

    public Locator getFooter() {
        return footer;
    }
}   