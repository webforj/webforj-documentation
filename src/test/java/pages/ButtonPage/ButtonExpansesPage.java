package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ButtonExpansesPage extends BasePage {

    private static final String ROUTE = "buttonexpanses";

    private final Locator expanseDropdown;
    private final Locator expanseChoiceBox;
    private final Locator expanseButton;
    
    public ButtonExpansesPage(Page page) {
        super(page);

        expanseDropdown = page.locator("dwc-choicebox[dwc-id='11'] >> dwc-dropdown");
        expanseChoiceBox = page.locator("dwc-listbox >> li");
        expanseButton = page.locator("dwc-button[dwc-id='12']");
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