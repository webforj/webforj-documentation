package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TabbedPanePage extends BasePage {

    // Activation Elements
    private final Locator activationToggle;
    private final Locator activationTabbedPane;
    private final Locator activationDashboardTab;
    private final Locator activationOrdersTab;

    // Alignment Elements
    private final Locator alignmentDropdown;
    private final Locator alignmentListBox;
    private final Locator alignmentTabbedPane;

    // Border Elements
    private final Locator hideBorderToggle;
    private final Locator hideActiveIndicatorToggle;
    private final Locator borderTabbedPane;
    private final Locator borderDashboardTab;
    private final Locator borderOrdersTab;

    // Expanses and Themes Elements
    private final Locator themesDropdown;
    private final Locator expansesDropdown;
    private final Locator expanseThemeListBox;
    private final Locator expanseThemeTabbedPane;
    private final Locator expanseThemeDashboardTab;

    // Placement Elements
    private final Locator placementDropdown;
    private final Locator placementListBox;
    private final Locator placementTabbedPane;

    public TabbedPanePage(Page page) {
        super(page);

        // Activation (elements from ActivationIT)
        activationToggle = page.locator("#radio-1");
        activationTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='12']");
        activationDashboardTab = activationTabbedPane.locator("dwc-tab").nth(0);
        activationOrdersTab = activationTabbedPane.locator("dwc-tab").nth(1);

        // Alignment (elements from AlignmentIT)
        alignmentDropdown = page.locator("dwc-dropdown.hydrated");
        alignmentListBox = page.locator("dwc-listbox");
        alignmentTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='12']");

        // Border (elements from BorderIT)
        hideBorderToggle = page.locator("#radio-1");
        hideActiveIndicatorToggle = page.locator("#radio-2");
        borderTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='13']");
        borderDashboardTab = borderTabbedPane.locator("dwc-tab").nth(0);
        borderOrdersTab = borderTabbedPane.locator("dwc-tab").nth(1);

        // Expanses and Themes (elements from ExpansesAndThemesIT)
        themesDropdown = page.locator("dwc-choicebox[dwc-id='12'] >> dwc-dropdown");
        expansesDropdown = page.locator("dwc-choicebox[dwc-id='13'] >> dwc-dropdown");
        expanseThemeListBox = page.locator("dwc-listbox");
        expanseThemeTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='14']");
        expanseThemeDashboardTab = expanseThemeTabbedPane.locator("dwc-tab").nth(0);

        // Placement (elements from TabbedPanePlacementIT)
        placementDropdown = page.locator("dwc-dropdown.hydrated");
        placementListBox = page.locator("dwc-listbox");
        placementTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='12']");
    }

    // Activation Getters
    public Locator getActivationToggle() {
        return activationToggle;
    }

    public Locator getActivationTabbedPane() {
        return activationTabbedPane;
    }

    public Locator getActivationDashboardTab() {
        return activationDashboardTab;
    }

    public Locator getActivationOrdersTab() {
        return activationOrdersTab;
    }

    // Alignment Getters
    public Locator getAlignmentDropdown() {
        return alignmentDropdown;
    }

    public Locator getAlignmentListBox() {
        return alignmentListBox;
    }

    public Locator getAlignmentTabbedPane() {
        return alignmentTabbedPane;
    }

    // Border Getters
    public Locator getHideBorderToggle() {
        return hideBorderToggle;
    }

    public Locator getHideActiveIndicatorToggle() {
        return hideActiveIndicatorToggle;
    }

    public Locator getBorderTabbedPane() {
        return borderTabbedPane;
    }

    public Locator getBorderDashboardTab() {
        return borderDashboardTab;
    }

    public Locator getBorderOrdersTab() {
        return borderOrdersTab;
    }

    // Expanses and Themes Getters
    public Locator getThemesDropdown() {
        return themesDropdown;
    }

    public Locator getExpansesDropdown() {
        return expansesDropdown;
    }

    public Locator getExpanseThemeListBox() {
        return expanseThemeListBox;
    }

    public Locator getExpanseThemeTabbedPane() {
        return expanseThemeTabbedPane;
    }

    public Locator getExpanseThemeDashboardTab() {
        return expanseThemeDashboardTab;
    }

    // Placement Getters
    public Locator getPlacementDropdown() {
        return placementDropdown;
    }

    public Locator getPlacementListBox() {
        return placementListBox;
    }

    public Locator getPlacementTabbedPane() {
        return placementTabbedPane;
    }
} 