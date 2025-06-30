package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ButtonPage.ButtonThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonThemesIT extends BaseTest {

    private ButtonThemesPage buttonPage;

    @BeforeEach
    public void setupButtonThemes() {
        navigateToRoute(ButtonThemesPage.getRoute());
        buttonPage = new ButtonThemesPage(page);

    }

    @BrowserTest
    public void testDangerThemedButtonVisibleClickableAndStyledCorrectly() {
        assertThat(buttonPage.getDangerThemeButton()).isVisible();
        assertThat(buttonPage.getDangerThemeButton()).isEnabled();
        assertThat(buttonPage.getDangerThemeButton()).hasCSS("background-color", "rgb(204, 10, 0)");
        assertThat(buttonPage.getDangerThemeButton()).hasCSS("color", "rgb(255, 255, 255)");
    }

    @BrowserTest
    public void testOutlinedWarningThemedButtonVisibleClickableAndStyledCorrectly() {
        assertThat(buttonPage.getOutlinedWarningThemeButton()).isVisible();
        assertThat(buttonPage.getOutlinedWarningThemeButton()).isEnabled();
        assertThat(buttonPage.getOutlinedWarningThemeButton()).hasCSS("background-color", "rgba(0, 0, 0, 0)");
        assertThat(buttonPage.getOutlinedWarningThemeButton()).hasCSS("color", "rgb(102, 82, 0)");
        assertThat(buttonPage.getOutlinedWarningThemeButton()).hasCSS("border-color", "rgba(0, 0, 0, 0)");
    }
} 