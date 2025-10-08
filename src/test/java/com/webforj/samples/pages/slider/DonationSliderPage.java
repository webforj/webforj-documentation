package com.webforj.samples.pages.slider;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DonationSliderPage {

    private static final String ROUTE = "donationslider";

    private final Locator donationLowerHandle;
    private final Locator donationButton;
    private final Locator confirmationToast;
    private final Locator twentyDollarsOption;

    public DonationSliderPage(Page page) {

        this.donationLowerHandle = page.getByRole(AriaRole.SLIDER);
        this.donationButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Donation"));
        this.confirmationToast = page.getByText(Pattern.compile("Thank you for your generous contribution of \\$\\d+!"));
        this.twentyDollarsOption = page.getByText("$20");
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