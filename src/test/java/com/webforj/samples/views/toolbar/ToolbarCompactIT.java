package com.webforj.samples.views.toolbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.toolbar.ToolbarCompactPage;
import com.webforj.samples.utils.WaitUtil;
import com.webforj.samples.views.BaseTest;

public class ToolbarCompactIT extends BaseTest {

    private ToolbarCompactPage toolbarCompactPage;

    @BeforeEach
    public void setupToolbarCompact() {
        navigateToRoute(ToolbarCompactPage.getRoute());
        toolbarCompactPage = new ToolbarCompactPage(page);
    }

    @Test
    public void testVisualHighlight() {
        WaitUtil.waitForVisible(toolbarCompactPage.getEnterpriseTab(),10000);

        toolbarCompactPage.getEnterpriseTab().click();
        assertThat(toolbarCompactPage.getEnterpriseTab()).hasAttribute("active", "");
        assertThat(toolbarCompactPage.getSalesTab()).not().hasAttribute("active", "");
    }
}
