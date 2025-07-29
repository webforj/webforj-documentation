package pages.flexlayout.container;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class FlexContainerBuilderPage extends BasePage {

    private static final String ROUTE = "flexcontainerbuilder";

    private final Locator containerBuilderInput;
    private final Locator containerBuilderContainer;
    private final Locator errorMessage;

    public FlexContainerBuilderPage(Page page) {
        super(page);

        containerBuilderInput = page.locator("dwc-numberfield:has-text('Number of Boxes') >> input[type='text']");
        containerBuilderContainer = page.locator(".button__container");
        errorMessage = page.locator("div.dwc-positioner.dwc-positioner--popover.dwc-positioner--visible");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getContainerBuilderInput() {
        return containerBuilderInput;
    }

    public Locator getContainerBuilderContainer() {
        return containerBuilderContainer;
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}