package tests.NavigatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.NavigatorPage.NavigatorLayoutPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NavigatorLayoutIT extends BaseTest {

    private NavigatorLayoutPage navigatorLayoutPage;

    @BeforeEach
    public void setupNavigatorLayout() {
        navigateToRoute(NavigatorLayoutPage.getRoute());
        navigatorLayoutPage = new NavigatorLayoutPage(page);
    }

    @BrowserTest
    public void testNoneDropdown() {
        navigatorLayoutPage.waitForVisiblePaginator();

        assertThat(navigatorLayoutPage.getNavigatorValue()).hasText("1 of 10");

        page.locator("dwc-button[part='button'] >> button").click();

        page.locator("li[role='option'] span[part='item-label']:has-text('NONE')").click();

        assertThat(navigatorLayoutPage.getNavigatorValue()).hasText(" ");

        assertThat(navigatorLayoutPage.getFirstButton()).isVisible();
        assertThat(navigatorLayoutPage.getPrevButton()).isVisible();
        assertThat(navigatorLayoutPage.getNextButton()).isVisible();
        assertThat(navigatorLayoutPage.getLastButton()).isVisible();
    }
}