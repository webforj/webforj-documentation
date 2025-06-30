package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SliderPage extends BasePage {

    // VolumeControl
    private final Locator muteIcon;
    private final Locator unmuteIcon;
    private final Locator lowerHandle;

    // DonationSlider
    private final Locator donationLowerHandle;
    private final Locator donationButton;
    private final Locator confirmationToast;
    private final Locator twentyDollarsOption;

    // MajorandMinorTick
    private final Locator majorTickInput;
    private final Locator minorTickInput;
    private final Locator majorTicks;
    private final Locator minorTicks;
    private final Locator tickSpacingControl;
    private final Locator tickToggle;
    private final Locator snapToggle;
    private final Locator snapThumb;

    // SliderOrientation
    private final Locator orientationSlider;

    // SliderTickAndNonTick
    private final Locator tenDegreeOption;
    private final Locator fortyDegreeOption;
    private final Locator sixtyDegreeOption;
    private final Locator ninetyDegreeOption;
    private final Locator labelsSlider;

    // SliderThemes
    private final Locator defaultThemeSlider;
    private final Locator dangerThemeSlider;
    private final Locator grayThemeSlider;
    private final Locator infoThemeSlider;
    private final Locator successThemeSlider;
    private final Locator warningThemeSlider;

    public SliderPage(Page page) {
        super(page);

        // VolumeControl
        muteIcon = page.locator("dwc-icon-button[dwc-id='12']");
        unmuteIcon = page.locator("dwc-icon-button[dwc-id='14']");
        lowerHandle = page.locator(".noUi-handle-lower");

        // DonationSlider
        donationLowerHandle = page.locator(".noUi-handle-lower");
        donationButton = page.locator("dwc-button[dwc-id='12']");
        confirmationToast = page.locator("dwc-toast-group");
        twentyDollarsOption = page.locator("text=$20");

        // MajorandMinorTick
        majorTickInput = page.locator("dwc-field[dwc-id='13'] >> input");
        minorTickInput = page.locator("dwc-field[dwc-id='14'] >> input");
        majorTicks = page.locator(".noUi-value-horizontal");
        minorTicks = page.locator(".noUi-marker-horizontal");
        tickSpacingControl = page.locator("dwc-slider[dwc-id='11'] >> div[part='control']");
        tickToggle = page.locator("dwc-radio[dwc-id='17']");
        snapToggle = page.locator("dwc-radio[dwc-id='16']");
        snapThumb = page.locator(".noUi-touch-area");

        // SliderOrientation
        orientationSlider = page.locator("dwc-slider[dwc-id='13']");

        // SliderTickAndNonTick
        tenDegreeOption = page.locator("div[data-value='10']");
        fortyDegreeOption = page.locator("div[data-value='40']");
        sixtyDegreeOption = page.locator("div[data-value='60']");
        ninetyDegreeOption = page.locator("div[data-value='90']");
        labelsSlider = page.locator("dwc-slider[dwc-id='11']");

        // SliderThemes
        defaultThemeSlider = page.locator("dwc-slider[dwc-id='11']");
        dangerThemeSlider = page.locator("dwc-slider[dwc-id='12']");
        grayThemeSlider = page.locator("dwc-slider[dwc-id='13']");
        infoThemeSlider = page.locator("dwc-slider[dwc-id='14']");
        successThemeSlider = page.locator("dwc-slider[dwc-id='15']");
        warningThemeSlider = page.locator("dwc-slider[dwc-id='16']");
    }

    // VolumeControl getters
    public Locator getMuteIcon() {
        return muteIcon;
    }

    public Locator getUnmuteIcon() {
        return unmuteIcon;
    }

    public Locator getLowerHandle() {
        return lowerHandle;
    }

    // DonationSlider getters
    public Locator getDonationLowerHandle() {
        return donationLowerHandle;
    }

    public Locator getDonationButton() {
        return donationButton;
    }

    public Locator getConfirmationToast() {
        return confirmationToast;
    }

    public Locator getTwentyDollarsOption() {
        return twentyDollarsOption;
    }

    // MajorandMinorTick getters
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

    // SliderOrientation getter
    public Locator getOrientationSlider() {
        return orientationSlider;
    }

    // SliderTickAndNonTick getters
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

    // SliderThemes getters
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
