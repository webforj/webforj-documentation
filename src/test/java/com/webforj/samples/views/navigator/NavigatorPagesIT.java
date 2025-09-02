package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.navigator.NavigatorPagesPage;
import com.webforj.samples.views.BaseTest;

public class NavigatorPagesIT extends BaseTest {

    private NavigatorPagesPage navigator;

    @BeforeEach
    public void setupNavigatorPage() {
        navigateToRoute(NavigatorPagesPage.getRoute());
        navigator = new NavigatorPagesPage(page);
    }

    @Test
    public void testVerifyEllipsis() {
        navigator.navigatorValue(4).click();

        assertThat(navigator.showingRange(31,40)).isVisible();
    }
}
