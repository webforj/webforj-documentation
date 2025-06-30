package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ElementsPage extends BasePage {

    private final Locator input;
    private final Locator label;
    private final Locator dialogBox;
    private final Locator OKButton;
    private final Locator secondDialogBox;

    public ElementsPage(Page page) {
        super(page);

        this.input = page.locator("input.element--input");
        this.label = page.locator("div.element--label");
        this.dialogBox = page.locator("dwc-dialog");
        this.secondDialogBox = page.locator("dwc-dialog[opened]");
        this.OKButton = page.locator("text=OK");
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

    public Locator getSecondDialogBox() {
        return secondDialogBox;
    }

}