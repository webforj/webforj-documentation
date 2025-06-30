package tests.ToastTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToastBasicsTestIT extends BaseTest {

    private ToastPage toastPage;

    @BeforeEach
    public void setupToastBasics() {
        page.navigate("https://docs.webforj.com/webforj/toast?");
        toastPage = new ToastPage(page);
    }

    @BrowserTest
    public void testToastCookies() {
        // Add your test implementation here
    }

    public void testToastBasics() {
        WaitUtil.waitForVisible(toastPage.getBasicToast());

        WaitUtil.waitForAttached(toastPage.getSpinner());
        WaitUtil.waitForAttached(toastPage.getBasicMessage());
        WaitUtil.waitForAttached(toastPage.getBasicButton());

        toastPage.getBasicButton().click();

        page.waitForTimeout(500);

        assertFalse(toastPage.getBasicToast().isVisible());
    }
} 