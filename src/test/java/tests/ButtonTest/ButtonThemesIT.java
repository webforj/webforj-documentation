package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ButtonPage.ButtonThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonThemesIT extends BaseTest {

    private ButtonThemesPage buttonThemesPage;

    @BeforeEach
    public void setupButtonThemes() {
        navigateToRoute(ButtonThemesPage.getRoute());
        buttonThemesPage = new ButtonThemesPage(page);

    }

    @BrowserTest
    public void testDangerThemedButtonVisibleClickableAndStyledCorrectly() {
        assertThat(buttonThemesPage.getDangerThemeButton()).isVisible();
        assertThat(buttonThemesPage.getDangerThemeButton()).isEnabled();
        assertThat(buttonThemesPage.getDangerThemeButton()).hasCSS("background-color", "rgb(204, 10, 0)");
        assertThat(buttonThemesPage.getDangerThemeButton()).hasCSS("color", "rgb(255, 255, 255)");
    }

    @BrowserTest
    public void testOutlinedWarningThemedButtonVisibleClickableAndStyledCorrectly() {
        assertThat(buttonThemesPage.getOutlinedWarningThemeButton()).isVisible();
        assertThat(buttonThemesPage.getOutlinedWarningThemeButton()).isEnabled();
        assertThat(buttonThemesPage.getOutlinedWarningThemeButton()).hasCSS("background-color", "rgba(0, 0, 0, 0)");
        assertThat(buttonThemesPage.getOutlinedWarningThemeButton()).hasCSS("color", "rgb(102, 82, 0)");
        assertThat(buttonThemesPage.getOutlinedWarningThemeButton()).hasCSS("border-color", "rgba(0, 0, 0, 0)");
    }
}