package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.TableFilteringPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableFilteringViewIT extends BaseTest {

    private TableFilteringPage tableFiltering;

    public void setupTableFiltering(SupportedLanguage language) {
        navigateToRoute(TableFilteringPage.getRoute(language));
        tableFiltering = new TableFilteringPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testValidTitleFiltering(SupportedLanguage language) {
        setupTableFiltering(language);
        tableFiltering.getTitleFilterInput().fill("Abbey Road");

        assertThat(tableFiltering.getTableRows()).hasCount(1);

        assertThat(tableFiltering.verifyTitle("Abbey Road")).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPartialTitleFiltering(SupportedLanguage language) {
        setupTableFiltering(language);
        tableFiltering.getTitleFilterInput().fill("Road");

        assertThat(tableFiltering.getTableRows()).hasCount(1);
        assertThat(tableFiltering.verifyTitle("Abbey Road")).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCaseSensitiveTitleFiltering(SupportedLanguage language) {
        setupTableFiltering(language);
        tableFiltering.getTitleFilterInput().fill("abbey ROAD");

        assertThat(tableFiltering.getTableRows()).hasCount(1);
        assertThat(tableFiltering.verifyTitle("Abbey Road")).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testNonexistentTitleFiltering(SupportedLanguage language) {
        setupTableFiltering(language);
        tableFiltering.getTitleFilterInput().fill("Nonexistent Album");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSpecialCharactersInSearchFiltering(SupportedLanguage language) {
        setupTableFiltering(language);
        tableFiltering.getTitleFilterInput().fill("#$!?");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSearchFiltersCaseSensitively(SupportedLanguage language) {
        setupTableFiltering(language);
        tableFiltering.getTitleFilterInput().fill("ABBA");

        assertThat(tableFiltering.getTableRows()).hasCount(0);
    }
}
