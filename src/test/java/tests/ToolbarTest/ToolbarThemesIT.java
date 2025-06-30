package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ToolbarThemesIT extends BaseTest {

    private ToolbarPage toolbarPage;

    @BeforeEach
    public void setupToolbarThemes() {
        page.navigate("https://docs.webforj.com/webforj/toolbartheme?");
        toolbarPage = new ToolbarPage(page);
    }

    @BrowserTest
    public void testThemes() {
        assertThat(toolbarPage.getDangerToolbar()).hasAttribute("theme", "danger");
        assertThat(toolbarPage.getDefaultToolbar()).hasAttribute("theme", "default");
        assertThat(toolbarPage.getGrayToolbar()).hasAttribute("theme", "gray");
        assertThat(toolbarPage.getInfoToolbar()).hasAttribute("theme", "info");
        assertThat(toolbarPage.getPrimaryToolbar()).hasAttribute("theme", "primary");
        assertThat(toolbarPage.getSuccessToolbar()).hasAttribute("theme", "success");
        assertThat(toolbarPage.getWarningToolbar()).hasAttribute("theme", "warning");

    }
} 