package pages.TabbedPanePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class TabbedPaneExpanseThemePage extends BasePage {

    private static final String ROUTE = RouteConfig.TABBED_PANE_EXPANSE_THEME;

    private final Locator themesDropdown;
    private final Locator expansesDropdown;
    private final Locator expanseThemeListBox;
    private final Locator expanseThemeTabbedPane;
    private final Locator expanseThemeDashboardTab;

    public TabbedPaneExpanseThemePage(Page page) {
        super(page);

        themesDropdown = page.locator("dwc-choicebox[dwc-id='12'] >> dwc-dropdown");
        expansesDropdown = page.locator("dwc-choicebox[dwc-id='13'] >> dwc-dropdown");
        expanseThemeListBox = page.locator("dwc-listbox");
        expanseThemeTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='14']");
        expanseThemeDashboardTab = expanseThemeTabbedPane.locator("dwc-tab").nth(0);
    }

    public static String getRoute() {
        return ROUTE;
    }

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
}