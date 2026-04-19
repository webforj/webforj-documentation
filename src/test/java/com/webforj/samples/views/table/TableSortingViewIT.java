package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.TableSortingPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableSortingViewIT extends BaseTest {

    private TableSortingPage tableSorting;

    public void setupTableSorting(SupportedLanguage language) {
        navigateToRoute(TableSortingPage.getRoute(language));
        tableSorting = new TableSortingPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSortAscendingAndDescendingOrder(SupportedLanguage language) {
        setupTableSorting(language);
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
