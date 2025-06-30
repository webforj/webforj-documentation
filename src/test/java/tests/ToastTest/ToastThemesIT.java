package tests.ToastTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ToastThemesIT extends BaseTest {

    private ToastPage toastPage;

    @BeforeEach
    public void setupToastThemes() {
        page.navigate("https://docs.webforj.com/webforj/toasttheme?");
        toastPage = new ToastPage(page);
    }

    @BrowserTest
    public void testToastThemes() {
        assertTrue(toastPage.getThemeMessageText().textContent().contains("The application has a new update available"));

        String customThemeAttribute = toastPage.getCustomThemeToast().getAttribute("class");
        assertTrue(customThemeAttribute.contains("custom-theme"));
    }
} 