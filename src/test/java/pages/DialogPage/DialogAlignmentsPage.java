package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogAlignmentsPage extends BasePage {

    private static final String ROUTE = "dialogalignmentsview";

    private final Locator selectTop;
    private final Locator selectCenter;
    private final Locator selectBottom;
    private final Locator selectAlignmentButton;
    private final Locator dialog;
    
    public DialogAlignmentsPage(Page page) {
        super(page);
        
        selectTop = page.locator("text=Top");
        selectCenter = page.locator("text=Center");
        selectBottom = page.locator("text=Bottom");
        selectAlignmentButton = page.locator("dwc-dropdown[part='dropdown']");
        dialog = page.locator("dwc-dialog[dwc-id='11']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSelectTop() {
        return selectTop;
    }

    public Locator getSelectCenter() {
        return selectCenter;
    }

    public Locator getSelectBottom() {
        return selectBottom;
    }

    public Locator getSelectAlignmentButton() {
        return selectAlignmentButton;
    }

    public Locator getDialog() {
        return dialog;
    }
} 