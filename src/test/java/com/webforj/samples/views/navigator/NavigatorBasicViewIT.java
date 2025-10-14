package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.navigator.NavigatorBasicPage;
import com.webforj.samples.views.BaseTest;

public class NavigatorBasicViewIT extends BaseTest {

    private NavigatorBasicPage navigator;

    @BeforeEach
    public void setupNavigatorBasics() {
        navigateToRoute(NavigatorBasicPage.getRoute());
        navigator = new NavigatorBasicPage(page);
    }

    @Test
    public void testRangeConsistencyAtStart() {

        navigator.clickNext();
        assertThat(navigator.navigatorValue(1)).isVisible();

        navigator.clickLast();
        assertThat(navigator.navigatorValue(10)).isVisible();

        navigator.clickPrev();
        assertThat(navigator.navigatorValue(9)).isVisible();

        navigator.clickFirst();
        assertThat(navigator.navigatorValue(0)).isVisible();

        navigator.clickPrev();
        assertThat(navigator.navigatorValue(0)).isVisible();

    }
}
