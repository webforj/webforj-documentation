package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.navigator.NavigatorBasicPage;
import com.webforj.samples.views.BaseTest;

public class NavigatorBasicIT extends BaseTest {

    private NavigatorBasicPage navigatorBasicPage;

    @BeforeEach
    public void setupNavigatorBasics() {
        navigateToRoute(NavigatorBasicPage.getRoute());
        navigatorBasicPage = new NavigatorBasicPage(page);
    }

    @Test
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
