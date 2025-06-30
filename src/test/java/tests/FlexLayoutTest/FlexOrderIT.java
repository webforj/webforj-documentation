package tests.FlexLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FlexLayoutPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexOrderIT extends BaseTest {

    private FlexLayoutPage flexPage;

    @BeforeEach
    public void setupFlexOrder() {
        page.navigate("https://docs.webforj.com/webforj/flexorder?");
        flexPage = new FlexLayoutPage(page);

    }

    @BrowserTest
    public void testEmptyOrderFieldTriggersValidationError() {
        flexPage.cleanField(flexPage.getOrderInput());
        flexPage.getSetOrderButton().click();
        assertThat(flexPage.getDwcAlert()).hasText("Order can not be empty.");
    }

    @BrowserTest
    public void testEnteringOneHighlightsFirstBox() {
        flexPage.cleanField(flexPage.getOrderInput());
        page.keyboard().type("1");
        flexPage.getSetOrderButton().click();

        assertThat(flexPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 1");

    }

    @BrowserTest
    public void testEnteringZeroPositionsBoxWithOrderZero() {
        flexPage.cleanField(flexPage.getOrderInput());
        page.keyboard().type("0");
        flexPage.getSetOrderButton().click();

        assertThat(flexPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 0");

    }

    @BrowserTest
    public void testNegativeOneNotAcceptedInOrderField() {
        flexPage.cleanField(flexPage.getOrderInput());
        page.keyboard().type("-1");
        flexPage.getSetOrderButton().click();

        assertThat(flexPage.getFlexOrderContainer().locator("dwc-button[theme='danger']")).hasText("Order: 1");
        assertThat(flexPage.getOrderInput()).hasValue("1");

    }

    @BrowserTest
    public void testSpecialCharactersNotAcceptedInOrderField() {
        flexPage.cleanField(flexPage.getOrderInput());
        page.keyboard().type("@Dd$#");
        flexPage.getSetOrderButton().click();

        assertThat(flexPage.getOrderInput()).hasValue("");

    }
} 