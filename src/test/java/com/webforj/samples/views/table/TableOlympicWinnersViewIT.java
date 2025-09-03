package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.table.TableOlympicWinnersPage;
import com.webforj.samples.views.BaseTest;

public class TableOlympicWinnersViewIT extends BaseTest {

    private TableOlympicWinnersPage tableOlympicWinnersPage;

    @BeforeEach
    public void setupTableOlympicWinners() {
        navigateToRoute(TableOlympicWinnersPage.getRoute());
        tableOlympicWinnersPage = new TableOlympicWinnersPage(page);
    }

    @Test
    public void testDynamicLoadingOnScroll() {
        String firstRowValue = tableOlympicWinnersPage.getFirstRow().getAttribute("data-row");
        assertThat(tableOlympicWinnersPage.getFirstRow()).hasAttribute("data-row", firstRowValue);

        tableOlympicWinnersPage.getLastRow().click();
        Locator firstRowAfterScrolling = tableOlympicWinnersPage.getFirstRow();
        assertThat(firstRowAfterScrolling).not().hasAttribute("data-row", firstRowValue);
    }
}
