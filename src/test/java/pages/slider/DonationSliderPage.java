package pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class DonationSliderPage extends BasePage {

    private static final String ROUTE = "donationslider"; ;

    private final Locator donationLowerHandle;
    private final Locator donationButton;
    private final Locator confirmationToast;
    private final Locator twentyDollarsOption;

    public DonationSliderPage(Page page) {
        super(page);

        donationLowerHandle = page.locator(".noUi-handle-lower");
        donationButton = page.locator("dwc-button:has-text('Confirm Donation')");
        confirmationToast = page.locator("dwc-toast-group");
        twentyDollarsOption = page.locator("text=$20");
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