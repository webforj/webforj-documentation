package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.table.TableOlympicWinnersPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableOlympicWinnersViewIT extends BaseTest {

    private TableOlympicWinnersPage tableOlympicWinnersPage;

    public void setupTableOlympicWinners(SupportedLanguage language) {
        navigateToRoute(TableOlympicWinnersPage.getRoute(language));
        tableOlympicWinnersPage = new TableOlympicWinnersPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDynamicLoadingOnScroll(SupportedLanguage language) {
        setupTableOlympicWinners(language);
        String firstRowValue = tableOlympicWinnersPage.getFirstRow().getAttribute("data-row");
        assertThat(tableOlympicWinnersPage.getFirstRow()).hasAttribute("data-row", firstRowValue);

        tableOlympicWinnersPage.getLastRow().click();
        Locator firstRowAfterScrolling = tableOlympicWinnersPage.getFirstRow();
        assertThat(firstRowAfterScrolling).not().hasAttribute("data-row", firstRowValue);
    }
}
