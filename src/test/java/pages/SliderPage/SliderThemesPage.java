package pages.SliderPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SliderThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.SLIDER_THEMES;

    private final Locator defaultThemeSlider;
    private final Locator dangerThemeSlider;
    private final Locator grayThemeSlider;
    private final Locator infoThemeSlider;
    private final Locator successThemeSlider;
    private final Locator warningThemeSlider;

    public SliderThemesPage(Page page) {
        super(page);

        defaultThemeSlider = page.locator("dwc-slider[dwc-id='11']");
        dangerThemeSlider = page.locator("dwc-slider[dwc-id='12']");
        grayThemeSlider = page.locator("dwc-slider[dwc-id='13']");
        infoThemeSlider = page.locator("dwc-slider[dwc-id='14']");
        successThemeSlider = page.locator("dwc-slider[dwc-id='15']");
        warningThemeSlider = page.locator("dwc-slider[dwc-id='16']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDefaultThemeSlider() {
        return defaultThemeSlider;
    }

    public Locator getDangerThemeSlider() {
        return dangerThemeSlider;
    }

    public Locator getGrayThemeSlider() {
        return grayThemeSlider;
    }

    public Locator getInfoThemeSlider() {
        return infoThemeSlider;
    }

    public Locator getSuccessThemeSlider() {
        return successThemeSlider;
    }

    public Locator getWarningThemeSlider() {
        return warningThemeSlider;
    }
}