package tests.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.slider.DonationSliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DonationSliderIT extends BaseTest {

    private DonationSliderPage sliderPage;

    @BeforeEach
    public void setupDonationSlider() {
        navigateToRoute(DonationSliderPage.getRoute());
        sliderPage = new DonationSliderPage(page);
    }

    @BrowserTest
    public void testDonation() {
        assertThat(sliderPage.getDonationLowerHandle()).hasAttribute("aria-valuenow", "50.0");
        sliderPage.getDonationButton().click();
        assertThat(sliderPage.getConfirmationToast()).hasText("Thank you for your generous contribution of $50!");

        sliderPage.getTwentyDollarsOption().click();
        assertThat(sliderPage.getDonationLowerHandle()).hasAttribute("aria-valuenow", "20.0");

        sliderPage.getDonationButton().click();
        assertThat(sliderPage.getConfirmationToast()).isVisible();
        assertThat(sliderPage.getConfirmationToast()).hasText("Thank you for your generous contribution of $20!");
    }
}