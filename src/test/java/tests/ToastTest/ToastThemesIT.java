package tests.ToastTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage.ToastThemesPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ToastThemesIT extends BaseTest {

    private ToastThemesPage toastThemesPage;

    @BeforeEach
    public void setupToastThemes() {
        navigateToRoute(ToastThemesPage.getRoute());
        toastThemesPage = new ToastThemesPage(page);
    }

    @BrowserTest
    public void testToastThemes() {
        assertTrue(toastThemesPage.getThemeMessageText().textContent().contains("The application has a new update available"));

        String customThemeAttribute = toastThemesPage.getCustomThemeToast().getAttribute("class");
        assertTrue(customThemeAttribute.contains("custom-theme"));
    }
}