package pages.SliderPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SliderOrientationPage extends BasePage {

    private static final String ROUTE = RouteConfig.SLIDER_ORIENTATION;

    private final Locator orientationSlider;

    public SliderOrientationPage(Page page) {
        super(page);

        orientationSlider = page.locator("dwc-slider[dwc-id='13']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getOrientationSlider() {
        return orientationSlider;
    }
}