package pages.SliderPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SliderTickSpacingPage extends BasePage {

    private static final String ROUTE = RouteConfig.SLIDER_TICK_SPACING;

    private final Locator majorTickInput;
    private final Locator minorTickInput;
    private final Locator majorTicks;
    private final Locator minorTicks;
    private final Locator tickSpacingControl;
    private final Locator tickToggle;
    private final Locator snapToggle;
    private final Locator snapThumb;
    private final Locator lowerHandle;

    public SliderTickSpacingPage(Page page) {
        super(page);

        majorTickInput = page.locator("dwc-field[dwc-id='13'] >> input");
        minorTickInput = page.locator("dwc-field[dwc-id='14'] >> input");
        majorTicks = page.locator(".noUi-value-horizontal");
        minorTicks = page.locator(".noUi-marker-horizontal");
        tickSpacingControl = page.locator("dwc-slider[dwc-id='11'] >> div[part='control']");
        tickToggle = page.locator("dwc-radio[dwc-id='17']");
        snapToggle = page.locator("dwc-radio[dwc-id='16']");
        snapThumb = page.locator(".noUi-touch-area");
        lowerHandle = page.locator(".noUi-handle-lower");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMajorTickInput() {
        return majorTickInput;
    }

    public Locator getMinorTickInput() {
        return minorTickInput;
    }

    public Locator getMajorTicks() {
        return majorTicks;
    }

    public Locator getMinorTicks() {
        return minorTicks;
    }

    public Locator getTickSpacingControl() {
        return tickSpacingControl;
    }

    public Locator getTickToggle() {
        return tickToggle;
    }

    public Locator getSnapToggle() {
        return snapToggle;
    }

    public Locator getSnapThumb() {
        return snapThumb;
    }

    public Locator getLowerHandle() {
        return lowerHandle;
    }
}