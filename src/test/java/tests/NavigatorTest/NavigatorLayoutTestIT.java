package tests.NavigatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.NavigatorPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NavigatorLayoutTestIT extends BaseTest {

    private NavigatorPage navigator;

    @BeforeEach
    public void setupNavigatorLayout() {
        page.navigate("https://docs.webforj.com/navigatorlayout?");
        navigator = new NavigatorPage(page);
    }

    @BrowserTest
    public void testNoneDropdown() {
        navigator.waitForVisiblePaginator();

        assertThat(navigator.getNavigatorValue()).hasText("1 of 10");

        page.locator("dwc-button[part='button'] >> button").click();

        page.locator("li[role='option'] span[part='item-label']:has-text('NONE')").click();

        assertThat(navigator.getNavigatorValue()).hasText(" ");

        assertThat(navigator.getFirstButton()).isVisible();
        assertThat(navigator.getPrevButton()).isVisible();
        assertThat(navigator.getNextButton()).isVisible();
        assertThat(navigator.getLastButton()).isVisible();
    }
} 