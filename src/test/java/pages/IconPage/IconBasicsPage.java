package pages.IconPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class IconBasicsPage extends BasePage {

    private static final String ROUTE = RouteConfig.ICON_BASICS;

    // IconBasics locators
    private final Locator messageIcon;
    private final Locator editIcon;
    private final Locator trashIcon;

    public IconBasicsPage(Page page) {
        super(page);

        // Initialize IconBasics locators
        messageIcon = page.locator("dwc-icon[dwc-id='11']");
        editIcon = page.locator("dwc-icon[dwc-id='12']");
        trashIcon = page.locator("dwc-icon[dwc-id='13']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // IconBasics getters
    public Locator getMessageIcon() {
        return messageIcon;
    }

    public Locator getEditIcon() {
        return editIcon;
    }

    public Locator getTrashIcon() {
        return trashIcon;
    }

    // Convenience methods for SVG elements within icons
    public Locator getMessageIconSvg() {
        return messageIcon.locator("svg");
    }

    public Locator getEditIconSvg() {
        return editIcon.locator("svg");
    }

    public Locator getTrashIconSvg() {
        return trashIcon.locator("svg");
    }
}