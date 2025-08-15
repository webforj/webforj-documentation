package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.table.TableSingleSelectionPage;
import com.webforj.samples.views.BaseTest;

public class TableSingleSelectionIT extends BaseTest {

    private TableSingleSelectionPage tableSingleSelectionPage;

    @BeforeEach
    public void setupTableSingleSelection() {
        navigateToRoute(TableSingleSelectionPage.getRoute());
        tableSingleSelectionPage = new TableSingleSelectionPage(page);
    }

    @Test
    public void testSingleItemSelectionAndConfirmationDialog() {
        tableSingleSelectionPage.getFirstArtist().click();
        
        assertThat(tableSingleSelectionPage.getHeaderMessage()).containsText("Record Number 000001");
        assertThat(tableSingleSelectionPage.getDialogMessage())
                .hasText("You have selected Mississippi Blues by John Hurt & The Ramblers");
    }
}
