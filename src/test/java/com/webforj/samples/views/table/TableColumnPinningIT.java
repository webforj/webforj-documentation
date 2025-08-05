package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.table.TableColumnPinningPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class TableColumnPinningIT extends BaseTest {

    private TableColumnPinningPage tablePage;

    @BeforeEach
    public void setupTableColumnPinning() {
        navigateToRoute(TableColumnPinningPage.getRoute());
        tablePage = new TableColumnPinningPage(page);
    }

    @BrowserTest
    public void testEditButton() {

        assertThat(tablePage.getEditButtonPosition()).hasAttribute("style", Pattern.compile(".*sticky; right: 0px;.*"));

        tablePage.getEditButton().click();
        assertThat(tablePage.getDialogBox()).hasText("You asked to edit record number 000001.");
    }
}
