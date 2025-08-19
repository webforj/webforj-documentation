package com.webforj.samples.views.table;

import com.webforj.samples.pages.table.DataTablePage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.views.BaseTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DataTableIT extends BaseTest {

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
    public void testEntriesPerPage() {
        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTen().click();
        assertThat(dataTable.getTableRows()).hasCount(10);

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTwentyfive().click();
        assertThat(dataTable.getTableRows()).hasCount(25);

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesHundred().click();
        assertThat(dataTable.getPaginationText()).containsText("Showing 1 to 100 of");
        assertThat(dataTable.getPaginationText()).containsText("entries");
    }

    @Test
    public void testSearchButtonFilter() {
        dataTable.searchAthlete(ATHLETE_NAME);

        assertThat(dataTable.getAthleteCells().first()).isVisible();
        assertThat(dataTable.getAthleteCells().first()).containsText(ATHLETE_NAME);

        assertThat(dataTable.getTableRows()).hasCount(3);
    }

    @Test
    public void testFilteringNonLatinAlphabetCharacters() {
        dataTable.searchAthlete(ATHLETE_WITH_DIACRITICS_LATIN);
        assertThat(dataTable.getAthleteCells().first()).containsText(ATHLETE_WITH_DIACRITICS_LATIN);

        dataTable.searchAthlete(ATHLETE_NAME_CYRILLIC);
        assertThat(dataTable.getTableRows()).hasCount(0);
    }

    @Test
    public void testSelectButton() {
        dataTable.getFirstCheckbox().click();
        assertThat(dataTable.getFirstCheckbox()).isChecked();
        dataTable.getFirstCheckbox().click();
        assertThat(dataTable.getFirstCheckbox()).not().isChecked();
    }

    @Test
    public void testPaginatorDisplaysCorrectly() {
        dataTable.getPaginatorLastPage().click();
        assertThat(dataTable.getLastPageNumber()).hasText("862");

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTwentyfive().click();
        assertThat(dataTable.getLastPageNumber()).hasText("345");

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesFifty().click();
        assertThat(dataTable.getLastPageNumber()).hasText("173");

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesHundred().click();
        assertThat(dataTable.getLastPageNumber()).hasText("87");
    }

    @Test
    public void testPaginatorNavigatesCorrectly() {
        dataTable.getPaginatorNextPage().click();
        assertThat(dataTable.getCurrentPageNavigator()).containsText("Showing 11 to 20");
    }

    @Test
    public void testPaginatorUpdatesCorrectlyBySearchFilter() {
        dataTable.searchAthlete(ATHLETE_NAME);
        assertThat(dataTable.getTableRows().first()).containsText(ATHLETE_NAME);
        assertThat(dataTable.goToSpecificPage(2)).not().isVisible();
        assertThat(dataTable.getPaginationText()).hasText("Showing 1 to 3 of 3 entries");
        assertThat(dataTable.getPaginatorPreviousPage()).isDisabled();
        assertThat(dataTable.getPaginatorFirstPage()).isDisabled();
        assertThat(dataTable.getPaginatorNextPage()).isDisabled();
        assertThat(dataTable.getPaginatorLastPage()).isDisabled();
    }

    @Test
    public void testPaginatorNavigatesCorrectlyToSpecificPage() {
        dataTable.goToSpecificPage(4).click();
        assertThat(dataTable.getCurrentPageNavigator()).containsText("Showing 31 to 40");
    }

    @Test
    public void testPaginatorNavigatesLastAndFirstPage() {
        dataTable.getPaginatorLastPage().click();
        assertThat(dataTable.goToSpecificPage(862)).isVisible();

        dataTable.getPaginatorFirstPage().click();
        assertThat(dataTable.goToSpecificPage(1));
    }
}
