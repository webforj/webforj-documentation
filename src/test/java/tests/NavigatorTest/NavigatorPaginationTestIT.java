package tests.NavigatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.NavigatorPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NavigatorPaginationTestIT extends BaseTest {

    private NavigatorPage navigator;

    @BeforeEach
    public void setupNavigatorPage() {
        page.navigate("https://docs.webforj.com/navigatorpages?");
        page.waitForLoadState();
        navigator = new NavigatorPage(page);

    }

    @BrowserTest
    public void testVerifyEllipsis() {
        navigator.assertCurrentPage(1);

        Locator pageButtons = navigator.getEllipsisButtons();
        assertThat(pageButtons.first()).isVisible();

        String[] expectedFirstSet = { "1", "2", "3", "4", "5", "..." };
        for (int i = 0; i < expectedFirstSet.length; i++) {
            String text = pageButtons.nth(i).textContent().trim();
            assertEquals(expectedFirstSet[i], text,
                    "Button text at index " + i + " should match");
        }

        navigator.goToPage(4);

        page.waitForSelector("button[title='Goto page 4'][aria-current='true']");
        navigator.assertCurrentPage(4);

        pageButtons = navigator.getEllipsisButtons();
        assertThat(pageButtons.first()).isVisible();

        String[] expectedSecondSet = { "...", "2", "3", "4", "5", "6", "..." };
        for (int i = 0; i < expectedSecondSet.length; i++) {
            String text = pageButtons.nth(i).textContent().trim();
            assertEquals(expectedSecondSet[i], text,
                    "Button text at index " + i + " should match");
        }
    }

    @BrowserTest
    public void testVerifyPageDataMessage() {

        navigator.assertCurrentPage(1);

        Locator rangeMessage = navigator.getPageRangeMessage();

        navigator.goToPage(2);

        assertThat(rangeMessage).hasText("Showing 11 to 20 of 100");
    }
} 