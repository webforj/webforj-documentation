package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.table.TableRichContentPage;
import com.webforj.samples.views.BaseTest;

public class TableRichContentViewIT extends BaseTest {

    private TableRichContentPage tableRichContent;

    @BeforeEach
    public void setupTableRichContent() {
        navigateToRoute(TableRichContentPage.getRoute());
        tableRichContent = new TableRichContentPage(page);
    }

    @Test
    public void testMasterCheckbox() {

        tableRichContent.getMasterCheckBox().click();

        List<Locator> checkboxes = tableRichContent.getCheckboxes().all();

        for (Locator checkbox : checkboxes) {
            assertThat(checkbox).isChecked();
        }
        tableRichContent.getMasterCheckBox().click();

        for (Locator checkbox : checkboxes) {
            assertThat(checkbox).not().isChecked();
        }
    }

    @Test
    public void testIndividualCheckbox() {
        tableRichContent.getMasterCheckBox().click();

        assertThat(tableRichContent.getFirstCheckbox()).isChecked();
    }

    @Test
    void testTableImages() {
        Locator images = tableRichContent.getImages();
        int count = images.count();

        for (int i = 0; i < count; i++) {
            Locator img = images.nth(i);

            assertThat(img).hasJSProperty("complete", true);
            assertThat(img).hasJSProperty("naturalWidth", 32);
            assertThat(img).hasJSProperty("naturalHeight", 32);

        }
    }
}
