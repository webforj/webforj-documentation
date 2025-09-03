package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.slider.DonationSliderPage;
import com.webforj.samples.views.BaseTest;

public class DonationSliderViewIT extends BaseTest {

    private DonationSliderPage sliderPage;

    @BeforeEach
    public void setupDonationSlider() {
        navigateToRoute(DonationSliderPage.getRoute());
        sliderPage = new DonationSliderPage(page);
    }

    @Test
    public void testDonation() {
        sliderPage.getTwentyDollarsOption().click();
        assertThat(sliderPage.getDonationLowerHandle()).hasAttribute("aria-valuenow", "20.0");

        sliderPage.getDonationButton().click();
        assertThat(sliderPage.getConfirmationToast()).isVisible();
        assertThat(sliderPage.getConfirmationToast()).hasText("Thank you for your generous contribution of $20!");
    }
}
