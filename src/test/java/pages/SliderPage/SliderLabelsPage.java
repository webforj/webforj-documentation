package pages.SliderPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SliderLabelsPage extends BasePage {

    private static final String ROUTE = RouteConfig.SLIDER_LABELS;

    private final Locator tenDegreeOption;
    private final Locator fortyDegreeOption;
    private final Locator sixtyDegreeOption;
    private final Locator ninetyDegreeOption;
    private final Locator labelsSlider;

    public SliderLabelsPage(Page page) {
        super(page);

        tenDegreeOption = page.locator("div[data-value='10']");
        fortyDegreeOption = page.locator("div[data-value='40']");
        sixtyDegreeOption = page.locator("div[data-value='60']");
        ninetyDegreeOption = page.locator("div[data-value='90']");
        labelsSlider = page.locator("dwc-slider[orientation='horizontal']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTenDegreeOption() {
        return tenDegreeOption;
    }

    public Locator getFortyDegreeOption() {
        return fortyDegreeOption;
    }

    public Locator getSixtyDegreeOption() {
        return sixtyDegreeOption;
    }

    public Locator getNinetyDegreeOption() {
        return ninetyDegreeOption;
    }

    public Locator getLabelsSlider() {
        return labelsSlider;
    }
}