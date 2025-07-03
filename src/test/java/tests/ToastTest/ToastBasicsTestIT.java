package tests.ToastTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage.ToastBasicsPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToastBasicsTestIT extends BaseTest {

    private ToastBasicsPage toastBasicsPage;

    @BeforeEach
    public void setupToastBasics() {
        navigateToRoute(ToastBasicsPage.getRoute());
        toastBasicsPage = new ToastBasicsPage(page);
    }

    @BrowserTest
    public void testToastCookies() {
        // Add your test implementation here
    }

    public void testToastBasics() {
        WaitUtil.waitForVisible(toastBasicsPage.getBasicToast());

        WaitUtil.waitForAttached(toastBasicsPage.getSpinner());
        WaitUtil.waitForAttached(toastBasicsPage.getBasicMessage());
        WaitUtil.waitForAttached(toastBasicsPage.getBasicButton());

        toastBasicsPage.getBasicButton().click();

        page.waitForTimeout(500);

        assertFalse(toastBasicsPage.getBasicToast().isVisible());
    }
}