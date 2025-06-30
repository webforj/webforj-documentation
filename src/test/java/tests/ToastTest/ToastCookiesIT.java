package tests.ToastTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage;

import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToastCookiesIT extends BaseTest {

    private ToastPage toastPage;

    @BeforeEach
    public void setupToastCookies() {
        page.navigate("https://docs.webforj.com/webforj/toastcookies?");
        toastPage = new ToastPage(page);
    }

    @BrowserTest
    public void testToastCookies() {
        WaitUtil.waitForVisible(toastPage.getCookieIcon());

        String expectedCookieText = "We use cookies to improve your experience. By clicking 'Accept all cookies', you agree to our Cookie Policy";
        assertEquals(expectedCookieText, toastPage.getCookieText().textContent());

        assertTrue(toastPage.getAcceptButton().isEnabled() && toastPage.getNecessaryButton().isEnabled());
    }
} 