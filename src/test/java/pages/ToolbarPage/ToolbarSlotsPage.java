package pages.ToolbarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToolbarSlotsPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOOLBAR_SLOTS;

    // Toolbar Slots Elements
    private final Locator slotsToolbar;
    private final Locator slotsTablerIcon;
    private final Locator slotsApplicationTitle;
    private final Locator toolbarTitle;
    private final Locator settingsButton;
    private final Locator profileButton;
    private final Locator slotsMainTitle;
    private final Locator slotsParagraph;

    public ToolbarSlotsPage(Page page) {
        super(page);

        // Toolbar Slots (dwc-id from ToolbarSlotsIT)
        slotsToolbar = page.locator("dwc-toolbar[dwc-id='13']");
        slotsTablerIcon = slotsToolbar.locator("dwc-icon-button[dwc-id='15']");
        slotsApplicationTitle = slotsToolbar.locator("h3[dwc-id='14']");
        toolbarTitle = slotsToolbar.locator("h3[dwc-id='18']");
        settingsButton = slotsToolbar.locator("dwc-icon-button[dwc-id='16']");
        profileButton = slotsToolbar.locator("dwc-icon-button[dwc-id='17']");
        slotsMainTitle = page.locator("dwc-app-layout[dwc-id='10'] >> h1[dwc-id='11']");
        slotsParagraph = page.locator("dwc-app-layout[dwc-id='10'] >>p[dwc-id='12']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toolbar Slots Getters
    public Locator getSlotsToolbar() {
        return slotsToolbar;
    }

    public Locator getSlotsTablerIcon() {
        return slotsTablerIcon;
    }

    public Locator getSlotsApplicationTitle() {
        return slotsApplicationTitle;
    }

    public Locator getToolbarTitle() {
        return toolbarTitle;
    }

    public Locator getSettingsButton() {
        return settingsButton;
    }

    public Locator getProfileButton() {
        return profileButton;
    }

    public Locator getSlotsMainTitle() {
        return slotsMainTitle;
    }

    public Locator getSlotsParagraph() {
        return slotsParagraph;
    }
}