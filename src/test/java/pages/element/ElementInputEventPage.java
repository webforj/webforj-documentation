package pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class ElementInputEventPage extends BasePage {

    private static final String ROUTE = "elementinputevent";

    private final Locator input;
    private final Locator label;
    private final Locator dialogBox;
    private final Locator OKButton;

    public ElementInputEventPage(Page page) {
        super(page);

        input = page.locator("input.element--input");
        label = page.locator("div.element--label");
        dialogBox = page.locator("dwc-dialog");
        OKButton = page.locator("text=OK");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInput() {
        return input;
    }

    public Locator getLabel() {
        return label;
    }

    public Locator getDialogBox() {
        return dialogBox;
    }

    public Locator getOKButton() {
        return OKButton;
    }
}