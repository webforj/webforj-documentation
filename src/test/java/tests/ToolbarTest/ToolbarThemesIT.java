package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage.ToolbarThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ToolbarThemesIT extends BaseTest {

    private ToolbarThemesPage toolbarThemesPage;

    @BeforeEach
    public void setupToolbarThemes() {
        navigateToRoute(ToolbarThemesPage.getRoute());
        toolbarThemesPage = new ToolbarThemesPage(page);
    }

    @BrowserTest
    public void testThemes() {
        assertThat(toolbarThemesPage.getDangerToolbar()).hasAttribute("theme", "danger");
        assertThat(toolbarThemesPage.getDefaultToolbar()).hasAttribute("theme", "default");
        assertThat(toolbarThemesPage.getGrayToolbar()).hasAttribute("theme", "gray");
        assertThat(toolbarThemesPage.getInfoToolbar()).hasAttribute("theme", "info");
        assertThat(toolbarThemesPage.getPrimaryToolbar()).hasAttribute("theme", "primary");
        assertThat(toolbarThemesPage.getSuccessToolbar()).hasAttribute("theme", "success");
        assertThat(toolbarThemesPage.getWarningToolbar()).hasAttribute("theme", "warning");

    }
}