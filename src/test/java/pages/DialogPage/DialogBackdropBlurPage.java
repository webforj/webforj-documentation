package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogBackdropBlurPage extends BasePage {

    private static final String ROUTE = "dialogbackdropblurview";

    // DialogBackdropBlur locators
    private final Locator dialog;
    private final Locator dialogHeader;
    private final Locator dialogContent;
    private final Locator blurChoiceBox;
    private final Locator backdrop;

    public DialogBackdropBlurPage(Page page) {
        super(page);
        
        // Initialize DialogBackdropBlur locators
        dialog = page.locator("dwc-dialog");
        dialogHeader = page.locator("div[part='header']");
        dialogContent = page.locator("div[part='content']");
        blurChoiceBox = page.locator("dwc-choicebox");
        backdrop = page.locator("div[part='backdrop']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DialogBackdropBlur getters
    public Locator getDialog() {
        return dialog;
    }

    public Locator getDialogHeader() {
        return dialogHeader;
    }

    public Locator getDialogContent() {
        return dialogContent;
    }

    public Locator getBlurChoiceBox() {
        return blurChoiceBox;
    }

    public Locator getBackdrop() {
        return backdrop;
    }
} 