package tests.ToastTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage.ToastCookiesPage;

import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToastCookiesIT extends BaseTest {

    private ToastCookiesPage toastCookiesPage;

    @BeforeEach
    public void setupToastCookies() {
        navigateToRoute(ToastCookiesPage.getRoute());
        toastCookiesPage = new ToastCookiesPage(page);
    }

    @BrowserTest
    public void testToastCookies() {
        WaitUtil.waitForVisible(toastCookiesPage.getCookieIcon());

        String expectedCookieText = "We use cookies to improve your experience. By clicking 'Accept all cookies', you agree to our Cookie Policy";
        assertEquals(expectedCookieText, toastCookiesPage.getCookieText().textContent());

        assertTrue(toastCookiesPage.getAcceptButton().isEnabled() && toastCookiesPage.getNecessaryButton().isEnabled());
    }
}