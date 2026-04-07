package com.webforj.samples.views.flexlayout.item;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.flexlayout.item.FlexOrderPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class FlexOrderViewIT extends BaseTest {

    private FlexOrderPage flexOrderPage;

    public void setupFlexOrder(SupportedLanguage language) {
        navigateToRoute(FlexOrderPage.getRoute(language));
        flexOrderPage = new FlexOrderPage(page);

    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testZeroOrderPositionsBoxAtStart(SupportedLanguage language) {
        setupFlexOrder(language);
        flexOrderPage.getSetOrderButton().click();
        assertThat(flexOrderPage.buttonValue(0)).isVisible();
    }
}
