package com.webforj.samples.views.table;

import com.webforj.samples.pages.table.DataTablePage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.views.BaseTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DataTableViewIT extends BaseTest {

    private static final String ATHLETE_NAME = "Michael Phelps";
    private static final String ATHLETE_WITH_DIACRITICS_LATIN = "Živko Gocic";
    private static final String ATHLETE_NAME_CYRILLIC = "Живко Гоцић"; // Živko Gocic

    private DataTablePage dataTable;

    @BeforeEach
    public void setupDataTable() {
        navigateToRoute(DataTablePage.getRoute());
        dataTable = new DataTablePage(page);
    }

    @Test
    public void testEntriesPerPageAndPaginator() {
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

    @Test
    public void testSearchButtonFilter() {
        dataTable.searchAthlete(ATHLETE_NAME);
        assertThat(dataTable.getTableRows()).hasCount(3);
    }

    @Test
    public void testFilteringNonLatinAlphabetCharacters() {
        dataTable.searchAthlete(ATHLETE_WITH_DIACRITICS_LATIN);
        assertThat(dataTable.getPaginationText("Showing 1 to 2 of 2 entries")).isVisible();

        dataTable.searchAthlete(ATHLETE_NAME_CYRILLIC);
        assertThat(dataTable.getTableRows()).hasCount(0);
    }

    @Test
    public void testPaginatorDisplaysCorrectlyPaginator() {
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

    @Test
    public void testPaginatorNavigatesCorrectlyPaginator() {
        dataTable.getPaginatorNextPage().click();
        assertThat(dataTable.getPaginationText("Showing 11 to 20 of 8618")).isVisible();
    }

    @Test
    public void testPaginatorUpdatesCorrectlyBySearchFilter() {
        dataTable.searchAthlete(ATHLETE_NAME);
        assertThat(dataTable.getPaginationText("Showing 1 to 3 of 3 entries")).isVisible();
        assertThat(dataTable.getPaginatorPreviousPage()).isDisabled();
        assertThat(dataTable.getPaginatorFirstPage()).isDisabled();
        assertThat(dataTable.getPaginatorNextPage()).isDisabled();
        assertThat(dataTable.getPaginatorLastPage()).isDisabled();
    }

    @Test
    public void testPaginatorNavigatesCorrectlyToSpecificPage() {
        dataTable.goToSpecificPage(4).click();
        assertThat(dataTable.getPaginationText("Showing 31 to 40 of 8618")).isVisible();
    }
}
