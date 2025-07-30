package pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class SliderTickSpacingPage extends BasePage {

    private static final String ROUTE = "slidertickspacing";

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

        majorTickInput = page.locator("dwc-field:has-text('Major Tick') >> input");
        minorTickInput = page.locator("dwc-field:has-text('Minor Tick') >> input");
        majorTicks = page.locator(".noUi-value-horizontal");
        minorTicks = page.locator(".noUi-marker-horizontal");
        tickSpacingControl = page.locator("dwc-slider >> div[part='control']");
        tickToggle = page.locator("dwc-radio:has-text('Show Ticks')");
        snapToggle = page.locator("dwc-radio:has-text('Snap to Ticks')");
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