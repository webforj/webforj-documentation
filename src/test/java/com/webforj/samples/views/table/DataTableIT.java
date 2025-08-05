package com.webforj.samples.views.table;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import com.webforj.samples.pages.table.DataTablePage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DataTableIT extends BaseTest {

    private static final String ATHLETE_NAME = "Michael Phelps";
    private static final String ATHLETE_WITH_DIACRITICS_LATIN = "Živko Gocic";
    private static final String ATHLETE_NAME_CYRILLIC = "Живко Гоцић"; // Živko Gocic
    private static final String ATHLETE_NAME_INVALID = "No Name";

    private DataTablePage dataTable;

    @BeforeEach
    public void setupDataTable() {
        navigateToRoute(DataTablePage.getRoute());
        dataTable = new DataTablePage(page);
    }

    @BrowserTest
    public void testEntriesPerPage() {
        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTen().click();
        assertThat(dataTable.getTableRows()).hasCount(10);

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesTwentyfive().click();
        assertThat(dataTable.getTableRows()).hasCount(25);

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesFifty().click();
        assertThat(dataTable.getPaginationText()).containsText("Showing 1 to 50 of");
        assertThat(dataTable.getPaginationText()).containsText("entries");

        dataTable.getEntriesDropdown().click();
        dataTable.getEntriesHundred().click();
        assertThat(dataTable.getPaginationText()).containsText("Showing 1 to 100 of");
        assertThat(dataTable.getPaginationText()).containsText("entries");
    }

    @BrowserTest
    public void testSearchButtonFilter() {
        dataTable.searchAthlete(ATHLETE_NAME);
        assertThat(dataTable.getTableRows().first()).containsText(ATHLETE_NAME);

        page.waitForTimeout(1000);

        int rowCount = dataTable.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            assertThat(dataTable.getTableRows().nth(i)).containsText(ATHLETE_NAME);
        }
    }

    @BrowserTest
    public void testFilteringNonLatinAlphabetCharacters() {
        dataTable.searchAthlete(ATHLETE_WITH_DIACRITICS_LATIN);
        page.waitForTimeout(1000);
        assertThat(dataTable.getTableRows().nth(0)).containsText(ATHLETE_WITH_DIACRITICS_LATIN);

        dataTable.searchAthlete(ATHLETE_NAME_CYRILLIC);
        page.waitForTimeout(1000);
        assertThat(dataTable.getTableRows()).hasCount(0);
        assertThat(dataTable.getTableRows()).not().isVisible();
    }

    @BrowserTest
    public void testSelectButton() {
        dataTable.getFirstCheckbox().click();
        assertThat(dataTable.getFirstCheckbox()).isChecked();
        dataTable.getFirstCheckbox().click();
        assertThat(dataTable.getFirstCheckbox()).not().isChecked();
    }

    @BrowserTest
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

    @BrowserTest
    public void testPaginatorNavigatesCorrectly() {
        dataTable.getPaginatorNextPage().click();
        assertThat(dataTable.getCurrentPageNavigator()).hasAttribute("current", "2");
    }

    @BrowserTest
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

    @BrowserTest
    public void testPaginatorNavigatesCorrectlyToSpecificPage() {
        dataTable.goToSpecificPage(4).click();
        assertThat(dataTable.getCurrentPageNavigator()).hasAttribute("current", "4");
    }

    @BrowserTest
    public void testPaginatorNavigatesLastAndFirstPage() {
        dataTable.getPaginatorLastPage().click();
        assertThat(dataTable.goToSpecificPage(862)).isVisible();

        dataTable.getPaginatorFirstPage().click();
        assertThat(dataTable.goToSpecificPage(1));
    }
}
