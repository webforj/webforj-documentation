package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.navigator.NavigatorTablePage;
import com.webforj.samples.views.BaseTest;

public class NavigatorTableIT extends BaseTest {

    private NavigatorTablePage navigatorTablePage;

    @BeforeEach
    public void setupNavigatorTest() {
        navigateToRoute(NavigatorTablePage.getRoute());
        navigatorTablePage = new NavigatorTablePage(page);

    }

    @Test
    public void testNavigationButtons() {
        navigatorTablePage.assertCurrentPage(1);

        navigatorTablePage.clickNext();
        page.waitForTimeout(300);
        navigatorTablePage.assertCurrentPage(2);

        navigatorTablePage.clickLast();
        page.waitForTimeout(300);
        navigatorTablePage.assertCurrentPage(5);

        navigatorTablePage.clickPrev();
        page.waitForTimeout(300);
        navigatorTablePage.assertCurrentPage(4);

        navigatorTablePage.clickFirst();
        page.waitForTimeout(300);
        navigatorTablePage.assertCurrentPage(1);
    }

    @Test
    public void testSpecificPageNavigation() {
        navigatorTablePage.goToPage(4);
        navigatorTablePage.assertCurrentPage(4);
    }

    @Test
    public void testDisableNavigationButtons() {
        navigatorTablePage.assertCurrentPage(1);
        assertThat(navigatorTablePage.getFirstButton()).hasAttribute("aria-disabled", "true");
        assertThat(navigatorTablePage.getPrevButton()).hasAttribute("aria-disabled", "true");

        navigatorTablePage.clickLast();
        navigatorTablePage.assertCurrentPage(5);
        assertThat(navigatorTablePage.getNextButton()).hasAttribute("aria-disabled", "true");
        assertThat(navigatorTablePage.getLastButton()).hasAttribute("aria-disabled", "true");
    }

    @Test
    public void testVerifyNavigatorData() {
        navigatorTablePage.assertCurrentPageData(1, "000001");

        navigatorTablePage.clickNext();
        page.waitForTimeout(1000);
        navigatorTablePage.assertCurrentPageData(2, "000013");
    }
}
