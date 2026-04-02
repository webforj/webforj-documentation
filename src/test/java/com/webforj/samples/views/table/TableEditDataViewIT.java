package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.TableEditDataPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableEditDataViewIT extends BaseTest {

    private TableEditDataPage tablePage;

    public void setupTableEditData(SupportedLanguage language) {
        navigateToRoute(TableEditDataPage.getRoute(language));
        tablePage = new TableEditDataPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testEditButtonTableEditData(SupportedLanguage language) {
        setupTableEditData(language);
        tablePage.getEditButton().click();

        tablePage.getInput().clear();
        tablePage.getInput().fill("Somebody I Used To Know");
        tablePage.getSaveButton().click();

        assertThat(tablePage.verifyTitle("Somebody I Used To Know")).isVisible();
    }
}
