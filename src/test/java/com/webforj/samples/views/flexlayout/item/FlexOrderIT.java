package com.webforj.samples.views.flexlayout.item;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.flexlayout.item.FlexOrderPage;
import com.webforj.samples.views.BaseTest;

public class FlexOrderIT extends BaseTest {

    private FlexOrderPage flexOrderPage;

    @BeforeEach
    public void setupFlexOrder() {
        navigateToRoute(FlexOrderPage.getRoute());
        flexOrderPage = new FlexOrderPage(page);

    }

    @Test
    public void testEnteringZeroPositionsBoxWithOrderZero() {
        flexOrderPage.getHostInput().click(); //it's zero by default

        flexOrderPage.getSetOrderButton().click();
        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']"))
                .hasText("Order: 0");

    }

    @Test
    public void testSpecialCharactersNotAcceptedInOrderField() {
        flexOrderPage.getHostInput().click();
        flexOrderPage.getOrderInput().clear();
        flexOrderPage.getOrderInput().fill("!@#$%");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).not()
                .hasText("Order: !@#$%");

    }

    @Test
    public void testCharactersNotAcceptedInOrderField() {
        flexOrderPage.getHostInput().click();
        flexOrderPage.getOrderInput().clear();
        flexOrderPage.getOrderInput().fill("ABC");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).not()
                .hasText("Order: ABC");

    }
}
