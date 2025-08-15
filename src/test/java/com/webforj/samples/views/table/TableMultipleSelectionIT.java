package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.table.TableMultiSelectionPage;
import com.webforj.samples.views.BaseTest;

public class TableMultipleSelectionIT extends BaseTest {

    private TableMultiSelectionPage multipleSelection;

    @BeforeEach
    public void setupTableMultiSelection() {
        navigateToRoute(TableMultiSelectionPage.getRoute());
        multipleSelection = new TableMultiSelectionPage(page);
    }

    @Test
    public void testSelectAllButton() {
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

        List<String> actualRecords = multipleSelection.getRecordItems().allTextContents();
        for (String record : expectedRecords) {
            assertTrue(actualRecords.contains(record));
        }

        multipleSelection.getOkButton().click();
        multipleSelection.getMasterCheckbox().click();
        assertThat(multipleSelection.getNoRecordsMessage()).isVisible();

        multipleSelection.getOkButton().click();
    }

    @Test
    public void testMultipleSelection() {
        multipleSelection.getCheckboxes().nth(1).click();
        multipleSelection.verifyRecordItems("Mississippi Blues");
        multipleSelection.getOkButton().click();

        multipleSelection.getCheckboxes().nth(2).click();
        multipleSelection.verifyRecordItems("Gold - Greatest Hits");
        multipleSelection.getOkButton().click();

        multipleSelection.getCheckboxes().nth(2).click();
        multipleSelection.verifyRecordItems("Gold - Greatest Hits", false);
    }
}
