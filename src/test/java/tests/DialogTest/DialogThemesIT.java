package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DialogPage.DialogThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DialogThemesIT extends BaseTest {

    private DialogThemesPage dialogThemesPage;

    @BeforeEach
    public void setupDialogThemes() {
        page.navigate("https://docs.webforj.com/webforj/dialogthemes?");
        dialogThemesPage = new DialogThemesPage(page);
    }

    @BrowserTest
    public void testThemes() {
        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getDangerTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "danger");

        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getDefaultTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "default");

        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getGrayTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "gray");

        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getInfoTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "info");

        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getPrimaryTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "primary");

        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getSuccessTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "success");

        dialogThemesPage.getSelectAlignmentButton().click();
        dialogThemesPage.getWarningTheme().click();
        assertThat(dialogThemesPage.getDialog()).hasAttribute("theme", "warning");
    }
}