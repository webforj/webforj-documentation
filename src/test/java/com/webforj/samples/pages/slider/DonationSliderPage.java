package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class DonationSliderPage extends BasePage {

    private static final String ROUTE = "donationslider";;

    private final Locator donationLowerHandle;
    private final Locator donationButton;
    private final Locator confirmationToast;
    private final Locator twentyDollarsOption;
    private final Locator shadowRootSlider;

    public DonationSliderPage(Page page) {
        super(page);

        this.shadowRootSlider = page.locator("dwc-slider");

        this.donationLowerHandle = shadowRootSlider.locator(".noUi-handle-lower");
        this.donationButton = page.locator("dwc-button[theme='gray']").locator("button");
        this.confirmationToast = page.locator("dwc-toast-group[placement='bottom']").locator("dwc-toast[theme='success']");
        this.twentyDollarsOption = shadowRootSlider.locator("text=$20");
    }

    public static String getRoute() {
        return ROUTE;
    }

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
}