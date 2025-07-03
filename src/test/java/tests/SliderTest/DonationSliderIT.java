package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage.DonationSliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DonationSliderIT extends BaseTest {

    private DonationSliderPage sliderPage;

    @BeforeEach
    public void setupDonationSlider() {
        page.navigate(DonationSliderPage.getRoute());
        sliderPage = new DonationSliderPage(page);
    }

    @BrowserTest
    public void testDonation() {
        assertThat(sliderPage.getDonationLowerHandle()).hasAttribute("aria-valuenow", "50.0");

        sliderPage.getTwentyDollarsOption().click();
        assertThat(sliderPage.getDonationLowerHandle()).hasAttribute("aria-valuenow", "20.0");

        sliderPage.getDonationButton().click();
        assertThat(sliderPage.getConfirmationToast()).isVisible();
    }
}