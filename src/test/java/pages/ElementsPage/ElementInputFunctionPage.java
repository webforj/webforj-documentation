package pages.ElementsPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class ElementInputFunctionPage extends BasePage {

    private static final String ROUTE = RouteConfig.ELEMENT_INPUT_FUNCTION;

    private final Locator dialogBox;
    private final Locator OKButton;
    private final Locator secondDialogBox;

    public ElementInputFunctionPage(Page page) {
        super(page);

        dialogBox = page.locator("dwc-dialog");
        OKButton = page.locator("text=OK");
        secondDialogBox = page.locator("dwc-dialog[opened]");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDialogBox() {
        return dialogBox;
    }

    public Locator getOKButton() {
        return OKButton;
    }

    public Locator getSecondDialogBox() {
        return secondDialogBox;
    }
}