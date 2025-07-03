package pages.ToastPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToastPlacementsPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOAST_PLACEMENT;

    // Toast Placements Elements
    private final Locator placementDropdown;
    private final Locator topListItem;
    private final Locator placementButton;
    private final Locator topToastGroup;

    /**
     * Constructor for ToastPlacementsPage.
     *
     * @param page the Playwright Page object
     */
    public ToastPlacementsPage(Page page) {
        super(page);

        // Toast Placements (elements from ToastPlacementsIT)
        placementDropdown = page.locator("dwc-dropdown[part=\"dropdown\"]");
        topListItem = page.locator("span[slot='label']:text('TOP')").nth(0);
        placementButton = page.locator("dwc-button[dwc-id='12']");
        topToastGroup = page.locator("dwc-toast-group[placement='top']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toast Placements Getters
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