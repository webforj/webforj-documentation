package tests.toolbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.toolbar.ToolbarCompactPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ToolbarCompactIT extends BaseTest {

    private ToolbarCompactPage toolbarCompactPage;

    @BeforeEach
    public void setupToolbarCompact() {
        navigateToRoute(ToolbarCompactPage.getRoute());
        toolbarCompactPage = new ToolbarCompactPage(page);
    }

    @BrowserTest
    public void testVisualHighlight() {

        toolbarCompactPage.getSalesTab().click();
        assertThat(toolbarCompactPage.getSalesTab()).hasAttribute("active", "");

        toolbarCompactPage.getEnterpriseTab().click();
        assertThat(toolbarCompactPage.getEnterpriseTab()).hasAttribute("active", "");
        assertThat(toolbarCompactPage.getSalesTab()).not().hasAttribute("active", "");
    }
}