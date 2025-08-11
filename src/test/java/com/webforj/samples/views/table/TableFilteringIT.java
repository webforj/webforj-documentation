package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.table.TableFilteringPage;
import com.webforj.samples.views.BaseTest;

public class TableFilteringIT extends BaseTest {

    private TableFilteringPage tableFiltering;

    @BeforeEach
    public void setupTableFiltering() {
        navigateToRoute(TableFilteringPage.getRoute());
        tableFiltering = new TableFilteringPage(page);
    }

    @Test
    public void testValidTitle() {
        tableFiltering.getTitleFilterInput().fill("Abbey Road");

        assertThat(tableFiltering.getTableRows()).hasCount(1);

        assertThat(tableFiltering.getFirstTitleCell()).hasText("Abbey Road");
    }

    @Test
    public void testPartialTitle() {
        tableFiltering.getTitleFilterInput().fill("Road");

        assertThat(tableFiltering.getTableRows()).hasCount(1);
        assertThat(tableFiltering.getFirstTitleCell()).hasText("Abbey Road");
    }

    @Test
    public void testCaseSensitiveTitle() {
        tableFiltering.getTitleFilterInput().fill("abbey ROAD");

        assertThat(tableFiltering.getTableRows()).hasCount(1);
        assertThat(tableFiltering.getFirstTitleCell()).hasText("Abbey Road");
    }

    @Test
    public void testNonexistentTitle() {
        tableFiltering.getTitleFilterInput().fill("Nonexistent Album");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }

    @Test
    public void testSpecialCharactersInSearch() {
        tableFiltering.getTitleFilterInput().fill("#$!?");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }

    @Test
    public void testSearchFiltersByTitleOnly() {
        tableFiltering.getTitleFilterInput().fill("ABBA");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }
}
