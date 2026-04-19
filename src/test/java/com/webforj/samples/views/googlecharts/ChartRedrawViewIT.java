package com.webforj.samples.views.googlecharts;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.googlecharts.ChartRedrawPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ChartRedrawViewIT extends BaseTest {

    private ChartRedrawPage chartRedraw;

    public void navigateToChartRedraw(SupportedLanguage language) {
        navigateToRoute(ChartRedrawPage.getRoute(language));
        chartRedraw = new ChartRedrawPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testChartValuesUpdated(SupportedLanguage language) {
        navigateToChartRedraw(language);
        chartRedraw.updateChart(140, 120, 100, 80);
        assertThat(chartRedraw.getValueForInstagram()).hasValue("140");
        assertThat(chartRedraw.getValueForTwitter()).hasValue("120");
        assertThat(chartRedraw.getValueForFacebook()).hasValue("100");
        assertThat(chartRedraw.getValueForLinkedIn()).hasValue("80");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInvalidNonNumberInputsIgnored(SupportedLanguage language) {
        navigateToChartRedraw(language);
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("abc");

        assertThat(chartRedraw.getValueForInstagram()).not().hasValue("abc");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInvalidSpecialCharactersIgnored(SupportedLanguage language) {
        navigateToChartRedraw(language);
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("$!#%&*");

        assertThat(chartRedraw.getValueForInstagram()).not().hasValue("$!#%&*");
    }
}
