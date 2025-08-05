package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.table.TableSortingPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class TableSortingIT extends BaseTest {

    private TableSortingPage tableSorting;

    @BeforeEach
    public void setupTableSorting() {
        navigateToRoute(TableSortingPage.getRoute());
        tableSorting = new TableSortingPage(page);
    }

    @BrowserTest
    public void testSortAscendingAndDescendingOrder() {
        String defaultTitle = "Mississippi Blues";
        String ascTitle = "Abbey Road";
        String descTitle = "War";

        assertThat(tableSorting.getFirstTitleCell()).hasText(defaultTitle);

        tableSorting.getTitleSorting().click();
        assertThat(tableSorting.getFirstTitleCell()).hasText(ascTitle);

        tableSorting.getTitleSorting().click();
        assertThat(tableSorting.getFirstTitleCell()).hasText(descTitle);

        tableSorting.getTitleSorting().click();
        assertThat(tableSorting.getFirstTitleCell()).hasText(defaultTitle);
    }
}
