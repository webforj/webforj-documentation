package tests.GoogleChartsTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class GoogleChartPagesTestsIT extends BaseTest {

    @BeforeEach
    public void setupToGoogleCharts() {
        page.navigate("https://docs.webforj.com/webforj/chart?");
    }

    @BrowserTest
    public void testPageVisibility() {
        Locator googleChart = page.locator("google-chart[dwc-id='11']");
        assertThat(googleChart).isVisible();
    }

    @BrowserTest
    public void testCountryRevenueDataGeoChart() {
        Locator textElements = page.locator("svg text");

        String firstValueText = textElements.nth(0).textContent();
        String secondValueText = textElements.nth(2).textContent();

        double firstValue = utils.StringUtils.parseNumber(firstValueText != null ? firstValueText : "");
        double secondValue = utils.StringUtils.parseNumber(secondValueText != null ? secondValueText : "");

        assertTrue(firstValue >= 0, "First value should be >= 0");
        assertTrue(firstValue <= 10000, "First value should be <= 10000");

        assertTrue(secondValue >= 0, "Second value should be >= 0");
        assertTrue(secondValue <= 10000, "Second value should be <= 10000");

        assertTrue(secondValue > firstValue, "Second value should be > first value");
    }
} 