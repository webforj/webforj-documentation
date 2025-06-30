package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogAutoFocusPage extends BasePage {

    private static final String ROUTE = "dialogautofocusview";

    private final Locator dialogBox;

    public DialogAutoFocusPage(Page page) {
        super(page);

        dialogBox = page.locator("dwc-field[dwc-id='13']");
        
    }

    public Locator getDialogBox() {
        return dialogBox;
    }

    public static String getRoute() {
        return ROUTE;
    }

} 