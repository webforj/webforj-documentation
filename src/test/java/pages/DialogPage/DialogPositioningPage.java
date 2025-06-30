package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogPositioningPage extends BasePage {

    private static final String ROUTE = "dialogpositioningview";

    // DialogPositioning locators
    private final Locator dialog;
    private final Locator dialogHeader;
    private final Locator dialogContent;
    private final Locator positionXInput;
    private final Locator positionYInput;
    private final Locator setPositionButton;

    public DialogPositioningPage(Page page) {
        super(page);
        
        // Initialize DialogPositioning locators
        dialog = page.locator("dwc-dialog");
        dialogHeader = page.locator("div[part='header']");
        dialogContent = page.locator("div[part='content']");
        positionXInput = page.locator("dwc-numberfield[label*='X']");
        positionYInput = page.locator("dwc-numberfield[label*='Y']");
        setPositionButton = page.locator("dwc-button:has-text('Set Position')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DialogPositioning getters
    public Locator getDialog() {
        return dialog;
    }

    public Locator getDialogHeader() {
        return dialogHeader;
    }

    public Locator getDialogContent() {
        return dialogContent;
    }

    public Locator getPositionXInput() {
        return positionXInput;
    }

    public Locator getPositionYInput() {
        return positionYInput;
    }

    public Locator getSetPositionButton() {
        return setPositionButton;
    }
} 