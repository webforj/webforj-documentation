package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.List;

import com.webforj.samples.pages.SupportedLanguage;
import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.table.TableRichContentPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableRichContentViewIT extends BaseTest {

    private TableRichContentPage tableRichContent;

    public void setupTableRichContent(SupportedLanguage language) {
        navigateToRoute(TableRichContentPage.getRoute(language));
        tableRichContent = new TableRichContentPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testMasterCheckboxRichContent(SupportedLanguage language) {
        setupTableRichContent(language);

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

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testIndividualCheckboxRichContent(SupportedLanguage language) {
        setupTableRichContent(language);
        tableRichContent.getMasterCheckBox().click();

        assertThat(tableRichContent.getFirstCheckbox()).isChecked();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    void testTableImagesRichContent(SupportedLanguage language) {
        setupTableRichContent(language);
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
