package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.slider.DonationSliderPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DonationSliderViewIT extends BaseTest {

    private DonationSliderPage sliderPage;

    public void setupDonationSlider(SupportedLanguage language) {
        navigateToRoute(DonationSliderPage.getRoute(language));
        sliderPage = new DonationSliderPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDonationSlider(SupportedLanguage language) {
        setupDonationSlider(language);
        sliderPage.getTwentyDollarsOption().click();
        assertThat(sliderPage.getDonationLowerHandle()).hasAttribute("aria-valuenow", "20.0");

        sliderPage.getDonationButton().click();
        assertThat(sliderPage.getConfirmationToast()).isVisible();
        assertThat(sliderPage.getConfirmationToast()).hasText("Thank you for your generous contribution of $20!");
    }
}
