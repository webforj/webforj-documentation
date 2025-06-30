package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FlexLayoutPage extends BasePage {

    private final Locator flexDirectionContainer;
    private final Locator flexDirectionDropdown;
    private final Locator listBox;

    private final Locator flexPositioningContainer;
    private final Locator flexPositioningDropdown;
    private final Locator flexOrderContainer;
    private final Locator orderInput;
    private final Locator setOrderButton;
    private final Locator containerBuilderInput;
    private final Locator containerBuilderContainer;
    private final Locator errorMessage;
    private final Locator dwcAlert;

    public FlexLayoutPage(Page page) {
        super(page);

        flexDirectionContainer = page.locator("div[dwc-id='13']");
        flexDirectionDropdown = page.locator("dwc-button[part='button']");
        flexPositioningContainer = page.locator("div[dwc-id='15']");
        flexPositioningDropdown = page.locator("dwc-choicebox[dwc-id='13'] >> dwc-button[part='button']");
        flexOrderContainer = page.locator("div[dwc-id='15']");
        orderInput = page.locator("dwc-numberfield[dwc-id='13'] >> input[type='text']");
        setOrderButton = page.locator("dwc-button[dwc-id='14']");
        containerBuilderInput = page.locator("dwc-numberfield[dwc-id='13'] >> input[type='text']");
        containerBuilderContainer = page.locator("div[dwc-id='20']");
        listBox = page.locator("dwc-listbox >> li");
        errorMessage = page.locator("div.dwc-positioner.dwc-positioner--popover.dwc-positioner--visible");
        dwcAlert = page.locator("dwc-alert-popover[theme='danger']");

    }

    public Locator getFlexPositioningContainer() {
        return flexPositioningContainer;
    }

    public Locator getFlexPositioningDropdown() {
        return flexPositioningDropdown;
    }

    public Locator getFlexOrderContainer() {
        return flexOrderContainer;
    }

    public Locator getOrderInput() {
        return orderInput;
    }

    public Locator getSetOrderButton() {
        return setOrderButton;
    }

    public Locator getContainerBuilderInput() {
        return containerBuilderInput;
    }

    public Locator getContainerBuilderContainer() {
        return containerBuilderContainer;
    }

    public Locator getFlexDirectionContainer() {
        return flexDirectionContainer;
    }

    public Locator getFlexDirectionDropdown() {
        return flexDirectionDropdown;
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }

    public Locator getDwcAlert() {
        return dwcAlert;
    }

    public Locator getListBox(String text) {
        return listBox.locator("text=\"" + text + "\"");
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");

    }

}