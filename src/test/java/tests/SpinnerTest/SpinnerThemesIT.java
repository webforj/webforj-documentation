package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerThemesIT extends BaseTest {

    private SpinnerPage spinnerPage;

    @BeforeEach
    public void setupSpinnerThemes() {
        page.navigate("https://docs.webforj.com/webforj/spinnerthemedemo?");
        spinnerPage = new SpinnerPage(page);
    }

    @BrowserTest
    public void testAllSpinnerThemesAreVisibleAndStyled() {
        assertThat(spinnerPage.getPrimaryThemeSpinner()).hasAttribute("theme", "primary");
        assertThat(spinnerPage.getSuccessThemeSpinner()).hasAttribute("theme", "success");
        assertThat(spinnerPage.getDangerThemeSpinner()).hasAttribute("theme", "danger");
        assertThat(spinnerPage.getWarningThemeSpinner()).hasAttribute("theme", "warning");
        assertThat(spinnerPage.getGrayThemeSpinner()).hasAttribute("theme", "gray");
        assertThat(spinnerPage.getInfoThemeSpinner()).hasAttribute("theme", "info");
        assertThat(spinnerPage.getDefaultThemeSpinner()).hasAttribute("theme", "default");
    }
} 