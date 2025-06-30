package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ToastPage extends BasePage {

    // Toast Basics Elements
    private final Locator basicToast;
    private final Locator spinner;
    private final Locator basicMessage;
    private final Locator basicButton;

    // Toast Placements Elements
    private final Locator placementDropdown;
    private final Locator topListItem;
    private final Locator placementButton;
    private final Locator topToastGroup;

    // Toast Cookies Elements
    private final Locator cookieIcon;
    private final Locator cookieText;
    private final Locator acceptButton;
    private final Locator necessaryButton;

    // Toast Themes Elements
    private final Locator customThemeToast;
    private final Locator themeMessageText;

    public ToastPage(Page page) {
        super(page);

        // Toast Basics (dwc-id from ToastBasicsTestIT)
        basicToast = page.locator("dwc-toast[dwc-id='10']");
        spinner = basicToast.locator("dwc-spinner");
        basicMessage = basicToast.locator("p[dwc-id='12']:has-text('System update failed')");
        basicButton = basicToast.locator("dwc-button[dwc-id=\"13\"]");

        // Toast Placements (elements from ToastPlacementsIT)
        placementDropdown = page.locator("dwc-dropdown[part=\"dropdown\"]");
        topListItem = page.locator("span[slot='label']:text('TOP')").nth(0);
        placementButton = page.locator("dwc-button[dwc-id='12']");
        topToastGroup = page.locator("dwc-toast-group[placement='top']");

        // Toast Cookies (elements from ToastCookiesIT)
        cookieIcon = page.locator("dwc-icon[name='cookie']");
        cookieText = page.locator("p[dwc-id='12']");
        acceptButton = page.locator("dwc-button[dwc-id='15']");
        necessaryButton = page.locator("dwc-button[dwc-id='16']");

        // Toast Themes (elements from ToastThemesIT)
        customThemeToast = page.locator("dwc-toast[dwc-id=\"10\"]");
        themeMessageText = customThemeToast.locator("div[part='message']");
    }

    // Toast Basics Getters
    public Locator getBasicToast() {
        return basicToast;
    }

    public Locator getSpinner() {
        return spinner;
    }

    public Locator getBasicMessage() {
        return basicMessage;
    }

    public Locator getBasicButton() {
        return basicButton;
    }

    // Toast Placements Getters
    public Locator getPlacementDropdown() {
        return placementDropdown;
    }

    public Locator getTopListItem() {
        return topListItem;
    }

    public Locator getPlacementButton() {
        return placementButton;
    }

    public Locator getTopToastGroup() {
        return topToastGroup;
    }

    // Toast Cookies Getters
    public Locator getCookieIcon() {
        return cookieIcon;
    }

    public Locator getCookieText() {
        return cookieText;
    }

    public Locator getAcceptButton() {
        return acceptButton;
    }

    public Locator getNecessaryButton() {
        return necessaryButton;
    }

    // Toast Themes Getters
    public Locator getCustomThemeToast() {
        return customThemeToast;
    }

    public Locator getThemeMessageText() {
        return themeMessageText;
    }
} 