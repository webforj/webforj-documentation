package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TablePages.TableFilteringPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableFilteringIT extends BaseTest {

    private TableFilteringPage tableFiltering;

    @BeforeEach
    public void setupTableFiltering() {
        navigateToRoute(TableFilteringPage.getRoute());
        tableFiltering = new TableFilteringPage(page);
    }

    @BrowserTest
    public void testValidTitle() {
        tableFiltering.getTitleFilterInput().fill("Abbey Road");

        assertThat(tableFiltering.getTableRows()).hasCount(1);

        assertThat(tableFiltering.getFirstTitleCell()).hasText("Abbey Road");
    }

    @BrowserTest
    public void testPartialTitle() {
        tableFiltering.getTitleFilterInput().fill("Road");

        assertThat(tableFiltering.getTableRows()).hasCount(1);
        assertThat(tableFiltering.getFirstTitleCell()).hasText("Abbey Road");
    }

    @BrowserTest
    public void testCaseSensitiveTitle() {
        tableFiltering.getTitleFilterInput().fill("abbey ROAD");

        assertThat(tableFiltering.getTableRows()).hasCount(1);
        assertThat(tableFiltering.getFirstTitleCell()).hasText("Abbey Road");
    }

    @BrowserTest
    public void testNonexistentTitle() {
        tableFiltering.getTitleFilterInput().fill("Nonexistent Album");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }

    @BrowserTest
    public void testSpecialCharactersInSearch() {
        tableFiltering.getTitleFilterInput().fill("#$!?");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }

    @BrowserTest
    public void testSearchFiltersByTitleOnly() {
        tableFiltering.getTitleFilterInput().fill("ABBA");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }
}