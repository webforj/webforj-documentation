package tests.NavigatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.NavigatorPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NavigatorTableTestIT extends BaseTest {

    private NavigatorPage navigator;

    @BeforeEach
    public void setupNavigatorTest() {
        page.navigate("https://docs.webforj.com/navigatortable?");
        page.waitForLoadState();
        navigator = new NavigatorPage(page);

    }

    @BrowserTest
    public void testNavigationButtons() {
        navigator.assertCurrentPage(1);

        navigator.clickNext();
        page.waitForTimeout(300);
        navigator.assertCurrentPage(2);

        navigator.clickLast();
        page.waitForTimeout(300);
        navigator.assertCurrentPage(5);

        navigator.clickPrev();
        page.waitForTimeout(300);
        navigator.assertCurrentPage(4);

        navigator.clickFirst();
        page.waitForTimeout(300);
        navigator.assertCurrentPage(1);
    }

    @BrowserTest
    public void testSpecificPageNavigation() {
        navigator.goToPage(4);
        navigator.assertCurrentPage(4);
    }

    @BrowserTest
    public void testDisableNavigationButtons() {
        navigator.assertCurrentPage(1);
        assertThat(navigator.getFirstButton()).hasAttribute("aria-disabled", "true");
        assertThat(navigator.getPrevButton()).hasAttribute("aria-disabled", "true");

        navigator.clickLast();
        navigator.assertCurrentPage(5);
        assertThat(navigator.getNextButton()).hasAttribute("aria-disabled", "true");
        assertThat(navigator.getLastButton()).hasAttribute("aria-disabled", "true");
    }

    @BrowserTest
    public void testVerifyNavigatorData() {
        navigator.assertCurrentPageData(1, "000001");

        navigator.clickNext();
        page.waitForTimeout(1000);
        navigator.assertCurrentPageData(2, "000013");
    }
} 