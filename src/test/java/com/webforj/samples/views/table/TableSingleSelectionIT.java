package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.options.AriaRole;

import com.webforj.samples.pages.table.TableSingleSelectionPage;
import com.webforj.samples.views.BaseTest;

public class TableSingleSelectionIT extends BaseTest {

    @BeforeEach
    public void setupTableSingleSelection() {
        navigateToRoute(TableSingleSelectionPage.getRoute());
    }

    @Test
    public void testSingleItemSelectionAndConfirmationDialog() {
        page.getByText("Mississippi Blues").click();
        assertThat(page.getByRole(AriaRole.BANNER)).containsText("Record Number 000001");
        assertThat(page.locator("section"))
                .hasText("You have selected Mississippi Blues by John Hurt & The Ramblers");
    }
}
