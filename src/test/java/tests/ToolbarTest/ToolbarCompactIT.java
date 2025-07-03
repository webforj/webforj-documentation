package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage.ToolbarCompactPage;
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
    public void testToolbarComponents() {

        assertThat(toolbarCompactPage.getTablerIcon()).isVisible();
        assertThat(toolbarCompactPage.getApplicationTitle()).isVisible();
        assertThat(toolbarCompactPage.getSalesTab()).isVisible();
        assertThat(toolbarCompactPage.getEnterpriseTab()).isVisible();
        assertThat(toolbarCompactPage.getPaymentTab()).isVisible();
        assertThat(toolbarCompactPage.getHistoryTab()).isVisible();

        assertThat(toolbarCompactPage.getMainTitle()).hasText("Application Title");
        assertThat(toolbarCompactPage.getParagraph()).hasText("Content goes here");
    }

    @BrowserTest
    public void testVisualHighlight() {

        toolbarCompactPage.getSalesTab().click();
        assertThat(toolbarCompactPage.getSalesTab()).hasAttribute("active", "");

        toolbarCompactPage.getEnterpriseTab().click();
        assertThat(toolbarCompactPage.getEnterpriseTab()).hasAttribute("active", "");

        toolbarCompactPage.getPaymentTab().click();
        assertThat(toolbarCompactPage.getPaymentTab()).hasAttribute("active", "");

        toolbarCompactPage.getHistoryTab().click();
        assertThat(toolbarCompactPage.getHistoryTab()).hasAttribute("active", "");
    }
}