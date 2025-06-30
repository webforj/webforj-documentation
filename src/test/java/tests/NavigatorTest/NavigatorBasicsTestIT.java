package tests.NavigatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.NavigatorPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NavigatorBasicsTestIT extends BaseTest {

    private NavigatorPage navigator;

    @BeforeEach
    public void setupNavigatorBasics() {
        page.navigate("https://docs.webforj.com/navigatorbasic?");
        page.waitForLoadState();
        navigator = new NavigatorPage(page);
    }

    @BrowserTest
    public void testRangeConsistencyTC008() {
        assertThat(navigator.getNavigatorValue()).hasText("Value: 0");

        navigator.clickNext();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 1");

        navigator.clickLast();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 10");

        navigator.clickPrev();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 9");

        navigator.clickFirst();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 0");
    }

    @BrowserTest
    public void testRangeConsistencyTC009() {
        navigator.clickPrev();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 0");
        navigator.clickFirst();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 0");

        navigator.clickLast();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 10");
        navigator.clickNext();
        assertThat(navigator.getNavigatorValue()).hasText("Value: 10");
    }
} 