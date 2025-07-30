package pages.toast;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ToastPlacementPage extends BasePage {

    private static final String ROUTE = "toastplacement";

    private final Locator placementDropdown;
    private final Locator topListItem;
    private final Locator placementButton;
    private final Locator topToastGroup;

    public ToastPlacementPage(Page page) {
        super(page);

        placementDropdown = page.locator("dwc-dropdown[part='dropdown']");
        topListItem = page.locator("span[slot='label']:text('TOP')").nth(0);
        placementButton = page.locator("dwc-button:has-text('Show Toast')");
        topToastGroup = page.locator("dwc-toast-group[placement='top']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPlacementDropdown() {
        return placementDropdown;
    }

    public Locator getTopListItem() {
        return topListItem;
    }

    public Locator getPlacementButton() {
        return placementButton;
    }

    public Locator getTopToastGroup() {
        return topToastGroup;
    }
}