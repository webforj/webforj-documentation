package pages.IconPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class IconActionsButtonsPage extends BasePage {

    private static final String ROUTE = "iconprefixsuffix";

    // IconActionsButtons locators
    private final Locator nextButton;
    private final Locator nextIcon;
    private final Locator filterButton;
    private final Locator filterIcon;

    public IconActionsButtonsPage(Page page) {
        super(page);
        
        // Initialize IconActionsButtons locators
        nextButton = page.locator("dwc-button[dwc-id='11']");
        nextIcon = page.locator("dwc-icon[dwc-id='12']");
        filterButton = page.locator("dwc-button[dwc-id='13']");
        filterIcon = page.locator("dwc-icon[dwc-id='14']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // IconActionsButtons getters
    public Locator getNextButton() {
        return nextButton;
    }

    public Locator getNextIcon() {
        return nextIcon;
    }

    public Locator getFilterButton() {
        return filterButton;
    }

    public Locator getFilterIcon() {
        return filterIcon;
    }

    // Convenience methods for nested icon access (as used in the original test)
    public Locator getNextButtonIcon() {
        return nextButton.locator("dwc-icon[dwc-id='12']");
    }

    public Locator getFilterButtonIcon() {
        return filterButton.locator("dwc-icon[dwc-id='14']");
    }

    // Convenience methods for SVG elements within icons
    public Locator getNextIconSvg() {
        return nextButton.locator("dwc-icon[dwc-id='12']").locator("svg");
    }

    public Locator getFilterIconSvg() {
        return filterButton.locator("dwc-icon[dwc-id='14']").locator("svg");
    }
} 