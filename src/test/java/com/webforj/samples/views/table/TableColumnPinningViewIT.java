package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.TableColumnPinningPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableColumnPinningViewIT extends BaseTest {

    private TableColumnPinningPage tablePage;

    public void setupTableColumnPinning(SupportedLanguage language) {
        navigateToRoute(TableColumnPinningPage.getRoute(language));
        tablePage = new TableColumnPinningPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testEditButtonTableColumnPinning(SupportedLanguage language) {
        setupTableColumnPinning(language);
        tablePage.getEditButton().click();
        assertThat(tablePage.getDialogBox()).hasText("You asked to edit record number 000001.");
    }
}
