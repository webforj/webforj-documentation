package tests.FlexLayoutTest.Container;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;


import pages.FlexLayoutPage.FlexContainerBuilderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexContainerBuilderIT extends BaseTest {

    private FlexContainerBuilderPage flexContainerBuilderPage;

    @BeforeEach
    public void setupContainerBuilder() {
        navigateToRoute(FlexContainerBuilderPage.getRoute());
        flexContainerBuilderPage = new FlexContainerBuilderPage(page);

    }

    @BrowserTest
    public void testEnteringSmallValidNumberPopulatesFiveBoxes() {
        flexContainerBuilderPage.cleanField(flexContainerBuilderPage.getContainerBuilderInput());
        page.keyboard().type("5");
        assertThat(flexContainerBuilderPage.getContainerBuilderContainer().locator(".demo__box")).hasCount(5);

    }

    @BrowserTest
    public void testEnteringZeroShowsValidationError() {
        flexContainerBuilderPage.cleanField(flexContainerBuilderPage.getContainerBuilderInput());
        page.keyboard().type("0");
        assertThat(flexContainerBuilderPage.getContainerBuilderContainer().locator(".demo__box")).hasCount(1);
        assertThat(flexContainerBuilderPage.getErrorMessage()).containsText("The value must be greater than or equal to 1");

    }

    @BrowserTest
    public void testEnteringNegativeOneShowsValidationError() {
        flexContainerBuilderPage.cleanField(flexContainerBuilderPage.getContainerBuilderInput());
        page.keyboard().type("-1");
        assertThat(flexContainerBuilderPage.getContainerBuilderContainer().locator(".demo__box")).hasCount(1);
        assertThat(flexContainerBuilderPage.getErrorMessage()).containsText("The value must be greater than or equal to 1");

    }

    @BrowserTest
    public void testEnteringSpecialCharactersShowsValidationError() {
        flexContainerBuilderPage.cleanField(flexContainerBuilderPage.getContainerBuilderInput());
        page.keyboard().type("Aa! #'");
        assertThat(flexContainerBuilderPage.getContainerBuilderInput()).hasValue("");
    }
}