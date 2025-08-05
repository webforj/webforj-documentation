package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.table.TableRichContentPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class TableRichContentIT extends BaseTest {

    private TableRichContentPage tableRichContent;

    @BeforeEach
    public void setupTableRichContent() {
        navigateToRoute(TableRichContentPage.getRoute());
        tableRichContent = new TableRichContentPage(page);
    }

    @BrowserTest
    public void testMasterCheckbox() {

        tableRichContent.getMasterCheckBox().click();

        List<Locator> checkboxes = page.locator("tbody div[part='cell-content-checkbox']").all();

        for (Locator checkbox : checkboxes) {
            assertThat(checkbox).isChecked();
        }
        tableRichContent.getMasterCheckBox().click();

        for (Locator checkbox : checkboxes) {
            assertThat(checkbox).not().isChecked();
        }
    }

    @BrowserTest
        public void testIndividualCheckbox() {
        tableRichContent.getCheckboxInput().click();

        assertThat(tableRichContent.getCheckboxInput()).isChecked();
    }

    @BrowserTest
    public void testTableImages() {

        for (int i = 0; i < tableRichContent.getImages().count(); i++) {
            Locator image = tableRichContent.getImages().nth(i);

            boolean isBroken = (boolean) image.evaluate(
                    "img => !img.loaded || img.width < 32 || img.height < 32");
            assertFalse(isBroken);
        }
    }
}
