package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import com.webforj.samples.pages.navigator.NavigatorPagesPage;
import com.webforj.samples.views.BaseTest;

public class NavigatorPagesIT extends BaseTest {

    private NavigatorPagesPage navigatorPaginationPage;

    @BeforeEach
    public void setupNavigatorPage() {
        navigateToRoute(NavigatorPagesPage.getRoute());
        navigatorPaginationPage = new NavigatorPagesPage(page);
    }

    @Test
    public void testVerifyEllipsis() {
        navigatorPaginationPage.goToPage(4);

        assertThat(navigatorPaginationPage.getCurrentPageButton()).hasText("4");
        assertThat(navigatorPaginationPage.getCurrentPageButton()).hasAttribute("aria-current", "true");

        String[] expectedSecondSet = { "...", "2", "3", "4", "5", "6", "..." };

        Locator buttons = navigatorPaginationPage.getPageButtons();
        assertThat(buttons).hasCount(expectedSecondSet.length);
    }
}
