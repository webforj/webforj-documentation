package pages.SliderPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SliderViewPage extends BasePage {

    private static final String ROUTE = RouteConfig.SLIDER_VIEW;

    private final Locator muteIcon;
    private final Locator unmuteIcon;
    private final Locator lowerHandle;

    public SliderViewPage(Page page) {
        super(page);

        muteIcon = page.locator("dwc-icon-button[dwc-id='12']");
        unmuteIcon = page.locator("dwc-icon-button[dwc-id='14']");
        lowerHandle = page.locator(".noUi-handle-lower");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMuteIcon() {
        return muteIcon;
    }

    public Locator getUnmuteIcon() {
        return unmuteIcon;
    }

    public Locator getLowerHandle() {
        return lowerHandle;
    }
}