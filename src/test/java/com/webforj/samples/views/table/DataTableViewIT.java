package com.webforj.samples.views.table;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.DataTablePage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.views.BaseTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DataTableViewIT extends BaseTest {

    private static final String ATHLETE_NAME = "Michael Phelps";
    private static final String ATHLETE_WITH_DIACRITICS_LATIN = "Živko Gocic";
    private static final String ATHLETE_NAME_CYRILLIC = "Живко Гоцић"; // Živko Gocic

    private DataTablePage dataTable;

    public void setupDataTable(SupportedLanguage language) {
        navigateToRoute(DataTablePage.getRoute(language));
        dataTable = new DataTablePage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testEntriesPerPageAndPaginator(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTen().click();
        assertThat(dataTable.getTableRows()).hasCount(10);

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTwentyfive().click();
        assertThat(dataTable.getTableRows()).hasCount(25);

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesHundred().click();
        assertThat(dataTable.getPaginationText("Showing 1 to 100 of 8618")).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSearchButtonFilterOnTable(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.searchAthlete(ATHLETE_NAME);
        assertThat(dataTable.getTableRows()).hasCount(3);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFilteringNonLatinAlphabetCharacters(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.searchAthlete(ATHLETE_WITH_DIACRITICS_LATIN);
        assertThat(dataTable.getPaginationText("Showing 1 to 2 of 2 entries")).isVisible();

        dataTable.searchAthlete(ATHLETE_NAME_CYRILLIC);
        assertThat(dataTable.getTableRows()).hasCount(0);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPaginatorDisplaysCorrectly(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.getPaginatorLastPage().click();
        assertThat(dataTable.goToSpecificPage(862)).isVisible();

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTwentyfive().click();
        assertThat(dataTable.goToSpecificPage(345)).isVisible();

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesFifty().click();
        assertThat(dataTable.goToSpecificPage(173)).isVisible();

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesHundred().click();
        assertThat(dataTable.goToSpecificPage(87)).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPaginatorNavigatesCorrectly(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.getPaginatorNextPage().click();
        assertThat(dataTable.getPaginationText("Showing 11 to 20 of 8618")).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPaginatorUpdatesCorrectlyBySearchFilter(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.searchAthlete(ATHLETE_NAME);
        assertThat(dataTable.getPaginationText("Showing 1 to 3 of 3 entries")).isVisible();
        assertThat(dataTable.getPaginatorPreviousPage()).isDisabled();
        assertThat(dataTable.getPaginatorFirstPage()).isDisabled();
        assertThat(dataTable.getPaginatorNextPage()).isDisabled();
        assertThat(dataTable.getPaginatorLastPage()).isDisabled();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPaginatorNavigatesCorrectlyToSpecificPage(SupportedLanguage language) {
        setupDataTable(language);
        dataTable.goToSpecificPage(4).click();
        assertThat(dataTable.getPaginationText("Showing 31 to 40 of 8618")).isVisible();
    }
}
