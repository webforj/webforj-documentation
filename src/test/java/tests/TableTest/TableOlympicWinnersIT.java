package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;
import com.webforj.samples.config.RouteConfig;
import pages.TablePages.TableOlympicWinnersViewPage;

public class TableOlympicWinnersIT extends BaseTest {

    private TableOlympicWinnersViewPage tableOlympicWinnersViewPage;

    @BeforeEach
    public void setupTableOlympicWinners() {
        navigateToRoute(RouteConfig.TABLE_OLYMPIC_WINNERS);
        tableOlympicWinnersViewPage = new TableOlympicWinnersViewPage(page);
    }

    @BrowserTest
    public void testColumnPinning() {

        String totalPosition = tableOlympicWinnersViewPage.getTotalHeader().evaluate("el => getComputedStyle(el).position").toString();
        assertEquals("sticky", totalPosition);

        String athletePosition = tableOlympicWinnersViewPage.getAthleteHeader().evaluate("el => getComputedStyle(el).position").toString();
        assertEquals("sticky", athletePosition);

        String right = tableOlympicWinnersViewPage.getTotalRow().evaluate("el => getComputedStyle(el).right").toString();
        assertEquals("0px", right);

        String left = tableOlympicWinnersViewPage.getAthleteRow().evaluate("el => getComputedStyle(el).left").toString();
        assertEquals("0px", left);
    }

    @BrowserTest
    public void testDynamicLoadingOnScroll() {
        String firstRowValue = tableOlympicWinnersViewPage.getFirstRow().getAttribute("data-row");
        assertThat(tableOlympicWinnersViewPage.getFirstRow()).hasAttribute("data-row", firstRowValue);

        tableOlympicWinnersViewPage.getLastRow().click();
        Locator firstRowAfterScrolling = tableOlympicWinnersViewPage.getRows().first();
        assertThat(firstRowAfterScrolling).not().hasAttribute("data-row", firstRowValue);
    }
}