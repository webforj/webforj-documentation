package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.navigator.NavigatorPagesPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class NavigatorPagesIT extends BaseTest {

    private NavigatorPagesPage navigatorPaginationPage;

    @BeforeEach
    public void setupNavigatorPage() {
        navigateToRoute(NavigatorPagesPage.getRoute());
        navigatorPaginationPage = new NavigatorPagesPage(page);
    }

    @BrowserTest
    public void testVerifyEllipsis() {
        navigatorPaginationPage.assertCurrentPage(1);

        Locator pageButtons = navigatorPaginationPage.getEllipsisButtons();
        assertThat(pageButtons.first()).isVisible();

        String[] expectedFirstSet = { "1", "2", "3", "4", "5", "..." };
        for (int i = 0; i < expectedFirstSet.length; i++) {
            String text = pageButtons.nth(i).textContent().trim();
            assertEquals(expectedFirstSet[i], text,
                    "Button text at index " + i + " should match");
        }

        navigatorPaginationPage.goToPage(4);

        page.waitForSelector("button[title='Goto page 4'][aria-current='true']");
        navigatorPaginationPage.assertCurrentPage(4);

        pageButtons = navigatorPaginationPage.getEllipsisButtons();
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

        navigatorPaginationPage.assertCurrentPage(1);
        navigatorPaginationPage.goToPage(2);
        assertThat(page.locator("p:has-text('Showing 11 to 20 of 100')")).isVisible();
    }
}
