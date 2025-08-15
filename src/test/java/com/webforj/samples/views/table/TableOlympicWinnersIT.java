package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.table.TableOlympicWinnersPage;
import com.webforj.samples.views.BaseTest;

public class TableOlympicWinnersIT extends BaseTest {

    private TableOlympicWinnersPage tableOlympicWinnersPage;

    @BeforeEach
    public void setupTableOlympicWinners() {
        navigateToRoute(TableOlympicWinnersPage.getRoute());
        tableOlympicWinnersPage = new TableOlympicWinnersPage(page);
    }

    @Test
    public void testColumnPinning() {
        assertThat(tableOlympicWinnersPage.getTotalHeader()).hasCSS("position", "sticky");
        assertThat(tableOlympicWinnersPage.getAthleteHeader()).hasCSS("position", "sticky");

        assertThat(tableOlympicWinnersPage.getTotalRow()).hasCSS("right", "0px");
        assertThat(tableOlympicWinnersPage.getAthleteRow()).hasCSS("left", "0px");
    }

    @Test
    public void testDynamicLoadingOnScroll() {
        String firstRowValue = tableOlympicWinnersPage.getFirstRow().getAttribute("data-row");
        assertThat(tableOlympicWinnersPage.getFirstRow()).hasAttribute("data-row", firstRowValue);

        tableOlympicWinnersPage.getLastRow().click();
        Locator firstRowAfterScrolling = tableOlympicWinnersPage.getRows().first();
        assertThat(firstRowAfterScrolling).not().hasAttribute("data-row", firstRowValue);
    }
}
