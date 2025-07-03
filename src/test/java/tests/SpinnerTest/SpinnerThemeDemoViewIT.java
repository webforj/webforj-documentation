package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerThemeDemoViewIT extends BaseTest {

    private SpinnerThemesPage spinnerPage;

    @BeforeEach
    public void setupSpinnerThemes() {
        page.navigate(SpinnerThemesPage.getRoute());
        spinnerPage = new SpinnerThemesPage(page);
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