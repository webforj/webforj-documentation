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
    public void testEnteringOneHighlightsFirstBox() {
        // Ensure input is ready then fill deterministically
        assertThat(flexOrderPage.getOrderInput()).isVisible();
        flexOrderPage.getOrderInput().fill("1");
        flexOrderPage.getSetOrderButton().click();
        // Wait until the danger button reflects the new order
        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']"))
                .hasText("Order: 1");

    }

    @Test
    public void testEnteringZeroPositionsBoxWithOrderZero() {
        assertThat(flexOrderPage.getOrderInput()).isVisible();
        flexOrderPage.getOrderInput().fill("0");
        flexOrderPage.getSetOrderButton().click();
        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']"))
                .hasText("Order: 0");

    }

    @Test
    public void testNegativeOneNotAcceptedInOrderField() {
        assertThat(flexOrderPage.getOrderInput()).isVisible();
        flexOrderPage.getOrderInput().fill("-1");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 1");
        assertThat(flexOrderPage.getOrderInput()).hasValue("1");

    }

    @Test
    public void testSpecialCharactersNotAcceptedInOrderField() {
        assertThat(flexOrderPage.getOrderInput()).isVisible();
        flexOrderPage.getOrderInput().fill("@Dd$#");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getOrderInput()).hasValue("");

    }
}
