package pages.IconPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class IconVariationsPage extends BasePage {

    private static final String ROUTE = RouteConfig.ICON_VARIATIONS;

    // IconVariations locators
    private final Locator envelopeIcon;
    private final Locator solidEnvelopeIcon;
    private final Locator instagramIcon;
    private final Locator calendarIcon;
    private final Locator filledCalendarIcon;

    public IconVariationsPage(Page page) {
        super(page);

        // Initialize IconVariations locators
        envelopeIcon = page.locator("dwc-icon[dwc-id='11']");
        solidEnvelopeIcon = page.locator("dwc-icon[dwc-id='12']");
        instagramIcon = page.locator("dwc-icon[dwc-id='13']");
        calendarIcon = page.locator("dwc-icon[dwc-id='14']");
        filledCalendarIcon = page.locator("dwc-icon[dwc-id='15']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // IconVariations getters
    public Locator getEnvelopeIcon() {
        return envelopeIcon;
    }

    public Locator getSolidEnvelopeIcon() {
        return solidEnvelopeIcon;
    }

    public Locator getInstagramIcon() {
        return instagramIcon;
    }

    public Locator getCalendarIcon() {
        return calendarIcon;
    }

    // Alternative getter with the typo from original test for compatibility
    public Locator getCalenderIcon() {
        return calendarIcon;
    }

    public Locator getFilledCalendarIcon() {
        return filledCalendarIcon;
    }

    // Alternative getter with the typo from original test for compatibility
    public Locator getFilledCalenderIcon() {
        return filledCalendarIcon;
    }

    // Convenience methods for SVG elements within icons
    public Locator getEnvelopeIconSvg() {
        return envelopeIcon.locator("svg");
    }

    public Locator getSolidEnvelopeIconSvg() {
        return solidEnvelopeIcon.locator("svg");
    }

    public Locator getInstagramIconSvg() {
        return instagramIcon.locator("svg");
    }

    public Locator getCalendarIconSvg() {
        return calendarIcon.locator("svg");
    }

    public Locator getFilledCalendarIconSvg() {
        return filledCalendarIcon.locator("svg");
    }
}