package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToolbarCompactIT extends BaseTest {

    private ToolbarPage toolbarPage;

    @BeforeEach
    public void setupToolbarCompact() {
        page.navigate("https://docs.webforj.com/webforj/toolbarcompact?");
        toolbarPage = new ToolbarPage(page);
    }

    @BrowserTest
    public void testToolbarComponents() {

        assertThat(toolbarPage.getTablerIcon()).isVisible();
        assertThat(toolbarPage.getApplicationTitle()).isVisible();
        assertThat(toolbarPage.getSalesTab()).isVisible();
        assertThat(toolbarPage.getEnterpriseTab()).isVisible();
        assertThat(toolbarPage.getPaymentTab()).isVisible();
        assertThat(toolbarPage.getHistoryTab()).isVisible();

        assertThat(toolbarPage.getMainTitle()).hasText("Application Title");
        assertThat(toolbarPage.getParagraph()).hasText("Content goes here");
    }

    @BrowserTest
    public void testVisualHighlight() {

        toolbarPage.getSalesTab().click();
        assertThat(toolbarPage.getSalesTab()).hasAttribute("active", "");

        toolbarPage.getEnterpriseTab().click();
        assertThat(toolbarPage.getEnterpriseTab()).hasAttribute("active", "");

        toolbarPage.getPaymentTab().click();
        assertThat(toolbarPage.getPaymentTab()).hasAttribute("active", "");

        toolbarPage.getHistoryTab().click();
        assertThat(toolbarPage.getHistoryTab()).hasAttribute("active", "");
    }
} 