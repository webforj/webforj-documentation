package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ButtonThemesPage extends BasePage {

    private static final String ROUTE = "buttonthemes";

    private final Locator dangerThemeButton;
    private final Locator outlinedWarningThemeButton;
    
    public ButtonThemesPage(Page page) {
        super(page);

        dangerThemeButton = page.locator("dwc-button[dwc-id='14'] >> button");
        outlinedWarningThemeButton = page.locator("dwc-button[dwc-id='28'] >> button");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDangerThemeButton() {
        return dangerThemeButton;
    }

    public Locator getOutlinedWarningThemeButton() {
        return outlinedWarningThemeButton;
    }
} 