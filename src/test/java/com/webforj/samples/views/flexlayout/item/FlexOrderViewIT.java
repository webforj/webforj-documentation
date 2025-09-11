package com.webforj.samples.views.flexlayout.item;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.flexlayout.item.FlexOrderPage;
import com.webforj.samples.views.BaseTest;

public class FlexOrderViewIT extends BaseTest {

    private FlexOrderPage flexOrderPage;

    @BeforeEach
    public void setupFlexOrder() {
        navigateToRoute(FlexOrderPage.getRoute());
        flexOrderPage = new FlexOrderPage(page);

    }

    @Test
    public void testEnteringZeroPositionsBoxWithOrderZero() {
        flexOrderPage.getSetOrderButton().click();
        assertThat(flexOrderPage.buttonValue(0)).isVisible();        
    }
}
