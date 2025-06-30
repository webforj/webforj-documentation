package tests.ToastTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage;

import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToastPlacementsIT extends BaseTest {

    private ToastPage toastPage;

    @BeforeEach
    public void setupToastPlacements() {
        page.navigate("https://docs.webforj.com/webforj/toastplacement?");
        toastPage = new ToastPage(page);
    }

    @BrowserTest
    public void testToastPlacements() {
        toastPage.getPlacementDropdown().click();
        toastPage.getTopListItem().waitFor();
        toastPage.getTopListItem().click();

        toastPage.getPlacementButton().click();

        WaitUtil.waitForVisible(toastPage.getTopToastGroup());
        assertThat(toastPage.getTopToastGroup()).isVisible();
    }
} 