package com.webforj.samples.views.googlecharts;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.googlecharts.ChartRedrawPage;
import com.webforj.samples.views.BaseTest;

public class ChartRedrawViewIT extends BaseTest {

    private ChartRedrawPage chartRedraw;

    @BeforeEach
    public void navigateToChartRedraw() {
        navigateToRoute(ChartRedrawPage.getRoute());
        chartRedraw = new ChartRedrawPage(page);
    }

    @Test
    public void testChartValuesUpdated() {
        chartRedraw.updateChart(140, 120, 100, 80);
        assertThat(chartRedraw.getValueForInstagram()).hasValue("140");
        assertThat(chartRedraw.getValueForTwitter()).hasValue("120");
        assertThat(chartRedraw.getValueForFacebook()).hasValue("100");
        assertThat(chartRedraw.getValueForLinkedIn()).hasValue("80");
    }

    @Test
    public void testInvalidNonNumberInputsIgnored() {
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("abc");

        assertThat(chartRedraw.getValueForInstagram()).not().hasValue("abc");
    }

    @Test
    public void testInvalidSpecialCharactersIgnored() {
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("$!#%&*");

        assertThat(chartRedraw.getValueForInstagram()).not().hasValue("$!#%&*");
    }
}
