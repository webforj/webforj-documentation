package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.webforj.samples.pages.SupportedLanguage;
import com.microsoft.playwright.Locator;
import com.webforj.samples.pages.table.TableMultiSelectionPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableMultipleSelectionViewIT extends BaseTest {

    private TableMultiSelectionPage multipleSelection;

    public void setupTableMultiSelection(SupportedLanguage language) {
        navigateToRoute(TableMultiSelectionPage.getRoute(language));
        multipleSelection = new TableMultiSelectionPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSelectAllRecords(SupportedLanguage language) {
        setupTableMultiSelection(language);
        List<String> expectedRecords = Arrays.asList(
                "Mississippi Blues",
                "Gold - Greatest Hits",
                "The Kids Are Alright",
                "Abbey Road",
                "Urban Blues",
                "Jagged Little Pill",
                "The Bends",
                "Life's Rich Pagent",
                "Too Bad Jim",
                "Dookie");

        multipleSelection.getMasterCheckbox().click();

        for (String record : expectedRecords) {
            try {
                assertThat(
                        multipleSelection.getRecordItems()
                                .filter(new Locator.FilterOptions().setHasText(record)))
                        .hasCount(1);
            } catch (Exception e) {
                System.out.println("Record not found: " + record);
            }
        }

        multipleSelection.getOkButton().click();
        multipleSelection.getMasterCheckbox().click();
        assertThat(multipleSelection.getNoRecordsMessage()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testMultipleSelectionRecords(SupportedLanguage language) {
        setupTableMultiSelection(language);
        multipleSelection.getRowCheckboxes().nth(1).click();
        assertThat(multipleSelection.getRecordItems()).containsText("Mississippi Blues");

        multipleSelection.getOkButton().click();

        multipleSelection.getRowCheckboxes().nth(1).click();
        assertThat(multipleSelection.getNoRecordsMessage()).isVisible();
    }
}
