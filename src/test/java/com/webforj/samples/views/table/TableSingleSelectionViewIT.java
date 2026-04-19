package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.TableSingleSelectionPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableSingleSelectionViewIT extends BaseTest {

    private TableSingleSelectionPage tableSingleSelectionPage;

    public void setupTableSingleSelection(SupportedLanguage language) {
        navigateToRoute(TableSingleSelectionPage.getRoute(language));
        tableSingleSelectionPage = new TableSingleSelectionPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSingleSelectionRecords(SupportedLanguage language) {
        setupTableSingleSelection(language);
        tableSingleSelectionPage.getFirstArtist().click();

        assertThat(tableSingleSelectionPage.getDialogMessage("Mississippi Blues", "John Hurt & The Ramblers"))
                .isVisible();
    }
}
