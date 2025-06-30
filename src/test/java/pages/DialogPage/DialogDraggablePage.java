package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogDraggablePage extends BasePage {

    private static final String ROUTE = "dialogdraggableview";

    private final Locator dialog;
    private final Locator dialogHeader;

    public DialogDraggablePage(Page page) {
        super(page);

        dialogHeader = page.locator("div[dwc-id='12']");
        dialog = page.locator("dwc-dialog[dwc-id='11']");
        
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDialog() {
        return dialog;
    }

    public Locator getDialogHeader() {
        return dialogHeader;
    }

} 