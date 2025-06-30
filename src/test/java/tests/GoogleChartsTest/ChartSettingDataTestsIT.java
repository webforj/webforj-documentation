package tests.GoogleChartsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ChartSettingDataTestsIT extends BaseTest {

    @BeforeEach
    public void setupToChartGallery() {
        page.navigate("https://docs.webforj.com/webforj/chartsettingdata?");
    }

    @BrowserTest
    public void testVerifyTitles() {
        Locator title = page.locator("svg g").nth(0);
        String titleText = title.textContent();
        assertEquals(titleText, "Sales Distribution by Region");

        List<String> expectedRegions = new LinkedList<>(Arrays.asList(
                "North America",
                "Europe",
                "Asia",
                "Latin America",
                "Middle East",
                "Africa"));

        Locator regionLocators = page.locator("g[column-id]:not([column-id='Sales Distribution by Region'])");

        int count = regionLocators.count();

        for (int i = 0; i < count; i++) {
            String actualRegion = regionLocators.nth(i).textContent().trim();
            String expectedRegion = expectedRegions.get(i);

            assertEquals(expectedRegion, actualRegion,
                    "Region mismatch at index " + i);
        }
    }
} 