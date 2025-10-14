package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.webforj.samples.views.BaseTest;

public class DialogSectionsViewIT extends BaseTest {

    @BeforeEach
    public void setupDialogSections() {
        navigateToRoute("dialogsections");
    }

    @Test
    public void testSectionsVisible() {
        Locator header = page.getByText("Header");
        Locator content = page.getByText("Content");
        Locator footer = page.getByText("Footer");

        assertThat(header).isVisible();
        assertThat(content).isVisible();
        assertThat(footer).isVisible();
    }
}
