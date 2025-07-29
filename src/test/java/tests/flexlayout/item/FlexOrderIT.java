package tests.flexlayout.item;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.flexlayout.item.FlexOrderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexOrderIT extends BaseTest {

    private FlexOrderPage flexOrderPage;

    @BeforeEach
    public void setupFlexOrder() {
        navigateToRoute(FlexOrderPage.getRoute());
        flexOrderPage = new FlexOrderPage(page);

    }

    @BrowserTest
    public void testEmptyOrderFieldTriggersValidationError() {
        flexOrderPage.cleanField(flexOrderPage.getOrderInput());
        flexOrderPage.getSetOrderButton().click();
        assertThat(flexOrderPage.getDwcAlert()).hasText("Order can not be empty.");
    }

    @BrowserTest
    public void testEnteringOneHighlightsFirstBox() {
        flexOrderPage.cleanField(flexOrderPage.getOrderInput());
        page.keyboard().type("1");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 1");

    }

    @BrowserTest
    public void testEnteringZeroPositionsBoxWithOrderZero() {
        flexOrderPage.cleanField(flexOrderPage.getOrderInput());
        page.keyboard().type("0");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 0");

    }

    @BrowserTest
    public void testNegativeOneNotAcceptedInOrderField() {
        flexOrderPage.cleanField(flexOrderPage.getOrderInput());
        page.keyboard().type("-1");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 1");
        assertThat(flexOrderPage.getOrderInput()).hasValue("1");

    }

    @BrowserTest
    public void testSpecialCharactersNotAcceptedInOrderField() {
        flexOrderPage.cleanField(flexOrderPage.getOrderInput());
        page.keyboard().type("@Dd$#");
        flexOrderPage.getSetOrderButton().click();

        assertThat(flexOrderPage.getOrderInput()).hasValue("");

    }
}