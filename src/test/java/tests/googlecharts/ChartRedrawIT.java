package tests.googlecharts;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.googlecharts.ChartRedrawPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ChartRedrawIT extends BaseTest {

    private ChartRedrawPage chartRedraw;

    @BeforeEach
    public void navigateToChartRedraw() {
        navigateToRoute(ChartRedrawPage.getRoute());

        try {
            chartRedraw = new ChartRedrawPage(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BrowserTest
    public void testChartUpdating() {
        assertThat(chartRedraw.getTitle()).hasText("Social Media Following");

        assertThat(chartRedraw.getValueForInstagram()).hasValue("100");
        assertThat(chartRedraw.getValueForTwitter()).hasValue("100");
        assertThat(chartRedraw.getValueForFacebook()).hasValue("100");
        assertThat(chartRedraw.getValueForLinkedIn()).hasValue("100");

        chartRedraw.updateChart(140, 120, 100, 80);
        assertThat(chartRedraw.getValueForInstagram()).hasValue("140");
        assertThat(chartRedraw.getValueForTwitter()).hasValue("120");
        assertThat(chartRedraw.getValueForFacebook()).hasValue("100");
        assertThat(chartRedraw.getValueForLinkedIn()).hasValue("80");

    }

    @BrowserTest
    public void testInvalidNegativeInput() {
        chartRedraw.getValueForInstagram().fill("-1");

        assertThat(chartRedraw.getWarningMessage()).isVisible();
    }

    @BrowserTest
    public void testInvalidNonNumberInput() {
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("abc");

        assertThat(chartRedraw.getValueForInstagram()).not().hasValue("abc");
    }

    @BrowserTest
    public void testInvalidSpecialCharacters() {
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("$!#%&*");

        assertThat(chartRedraw.getValueForInstagram()).not().hasValue("$!#%&*");
    }

    @BrowserTest
    public void testSmallSizeScreenScrolling() {
        page.setViewportSize(600, 300);

        assertThat(page.locator("html")).hasCSS("overflow-y", "visible");
        assertThat(page.locator("html")).hasCSS("overflow-x", "visible");
    }

    @BrowserTest
    public void testInvalidDecimalValues() {
        chartRedraw.cleanField(chartRedraw.getValueForInstagram());
        page.keyboard().type("5.4");

        assertThat(chartRedraw.getWarningMessage()).isVisible();
    }
}