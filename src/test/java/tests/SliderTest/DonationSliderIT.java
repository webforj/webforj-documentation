package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DonationSliderIT extends BaseTest {

    private SliderPage sliderPage;

    @BeforeEach
    public void setupDonationSlider() {
        page.navigate("https://docs.webforj.com/webforj/donationslider?");
        sliderPage = new SliderPage(page);
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