package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import com.webforj.samples.config.RouteConfig;

public class ButtonExpansesPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUTTON_EXPANSES;

    private final Locator expanseDropdown;
    private final Locator expanseChoiceBox;
    private final Locator expanseButton;

    public ButtonExpansesPage(Page page) {
        super(page);

        expanseDropdown = page.locator("dwc-dropdown[part='dropdown']");
        expanseChoiceBox = page.locator("dwc-listbox >> li");
        expanseButton = page.locator("dwc-button[alignment='center']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getExpanseDropdown() {
        return expanseDropdown;
    }

    public Locator getExpanseChoiceBox() {
        return expanseChoiceBox;
    }

    public Locator getExpanseButton() {
        return expanseButton;
    }
}