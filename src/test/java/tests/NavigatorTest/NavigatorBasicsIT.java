package tests.NavigatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.NavigatorPage.NavigatorBasicPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NavigatorBasicsIT extends BaseTest {

    private NavigatorBasicPage navigatorBasicPage;

    @BeforeEach
    public void setupNavigatorBasics() {
        navigateToRoute(NavigatorBasicPage.getRoute());
        page.waitForLoadState();
        navigatorBasicPage = new NavigatorBasicPage(page);
    }

    @BrowserTest
    public void testRangeConsistency() {
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 0");

        navigatorBasicPage.clickNext();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 1");

        navigatorBasicPage.clickLast();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 10");

        navigatorBasicPage.clickPrev();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 9");

        navigatorBasicPage.clickFirst();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 0");

        navigatorBasicPage.clickPrev();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 0");

        navigatorBasicPage.clickFirst();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 0");

        navigatorBasicPage.clickLast();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 10");

        navigatorBasicPage.clickNext();
        assertThat(navigatorBasicPage.getNavigatorValue()).hasText("Value: 10");
    }
}