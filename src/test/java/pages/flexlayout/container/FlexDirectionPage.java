package pages.flexlayout.container;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class FlexDirectionPage extends BasePage {

    private static final String ROUTE = "flexdirection";

    private final Locator flexDirectionContainer;
    private final Locator flexDirectionDropdown;
    private final Locator listBox;

    public FlexDirectionPage(Page page) {
        super(page);

        flexDirectionContainer = page.locator(".button__container--single-row");
        flexDirectionDropdown = page.locator("dwc-button[part='button']");
        listBox = page.locator("dwc-listbox >> li");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFlexDirectionContainer() {
        return flexDirectionContainer;
    }

    public Locator getFlexDirectionDropdown() {
        return flexDirectionDropdown;
    }

    public Locator getListBox(String text) {
        return listBox.locator("text=\"" + text + "\"");
    }
}