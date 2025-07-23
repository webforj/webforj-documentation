package pages.SliderPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class SliderViewPage extends BasePage {

    private static final String ROUTE = RouteConfig.SLIDER_VIEW;

    private final Locator muteIcon;
    private final Locator unmuteIcon;
    private final Locator lowerHandle;

    public SliderViewPage(Page page) {
        super(page);

        muteIcon = page.locator(".volume-off");
        unmuteIcon = page.locator("dwc-icon-button.volume-2");
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