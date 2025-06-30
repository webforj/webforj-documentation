package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LargeDataSetsIT extends BaseTest {

    @BeforeEach
    public void setupTableOlympicWinners() {
        page.navigate("https://docs.webforj.com/tableolympicwinners?");
    }

    @BrowserTest
    public void testColumnPinning() {
        Locator totalHeader = page.locator("th:nth-child(7)").first();
        Locator athleteHeader = page.locator("th:nth-child(1)").first();

        Locator totalRow = page.locator("td:nth-child(7)").first();
        Locator athleteRow = page.locator("td:nth-child(1)").first();

        String totalPosition = totalHeader.evaluate("el => getComputedStyle(el).position").toString();
        assertEquals("sticky", totalPosition);

        String athletePosition = athleteHeader.evaluate("el => getComputedStyle(el).position").toString();
        assertEquals("sticky", athletePosition);

        String right = totalRow.evaluate("el => getComputedStyle(el).right").toString();
        assertEquals("0px", right);

        String left = athleteRow.evaluate("el => getComputedStyle(el).left").toString();
        assertEquals("0px", left);
    }

    @BrowserTest
    public void testDynamicLoadingOnScroll() {
        Locator rows = page.locator("tr[data-row]");
        Locator firstRow = rows.first();
        Locator lastRow = rows.last();

        String firstRowValue = firstRow.getAttribute("data-row");
        assertThat(firstRow).hasAttribute("data-row", firstRowValue);

        lastRow.click();
        Locator firstRowAfterScrolling = rows.first();
        assertThat(firstRowAfterScrolling).not().hasAttribute("data-row", firstRowValue);
    }
} 