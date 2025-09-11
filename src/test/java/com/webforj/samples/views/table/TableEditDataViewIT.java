package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.table.TableEditDataPage;
import com.webforj.samples.views.BaseTest;

public class TableEditDataViewIT extends BaseTest {

    private TableEditDataPage tablePage;

    @BeforeEach
    public void setupTableEditData() {
        navigateToRoute(TableEditDataPage.getRoute());
        tablePage = new TableEditDataPage(page);
    }

    @Test
    public void testEditButton() {
        tablePage.getEditButton().click();

        tablePage.getInput().clear();
        tablePage.getInput().fill("Somebody I Used To Know");
        tablePage.getSaveButton().click();

        assertThat(tablePage.verifyTitle("Somebody I Used To Know")).isVisible();
    }
}
