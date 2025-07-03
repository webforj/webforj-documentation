package tests.GoogleChartsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import pages.GoogleChartsPages.ChartSettingDataViewPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ChartSettingDataIT extends BaseTest {

    private ChartSettingDataViewPage chartSettingDataPage;

    @BeforeEach
    public void setupToChartSettingData() {
        navigateToRoute(ChartSettingDataViewPage.getRoute());
        chartSettingDataPage = new ChartSettingDataViewPage(page);
    }

    @BrowserTest
    public void testVerifyTitles() {
        String titleText = chartSettingDataPage.getTitle().textContent();
        assertEquals(titleText, "Sales Distribution by Region");

        List<String> expectedRegions = new LinkedList<>(Arrays.asList(
                "North America",
                "Europe",
                "Asia",
                "Latin America",
                "Middle East",
                "Africa"));


        int count = chartSettingDataPage.getRegionLocators().count();

        for (int i = 0; i < count; i++) {
            String actualRegion = chartSettingDataPage.getRegionLocators().nth(i).textContent().trim();
            String expectedRegion = expectedRegions.get(i);

            assertEquals(expectedRegion, actualRegion,
                    "Region mismatch at index " + i);
        }
    }
}