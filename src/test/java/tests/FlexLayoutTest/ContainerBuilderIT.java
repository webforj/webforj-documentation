package tests.FlexLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Keyboard;

import pages.FlexLayoutPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ContainerBuilderIT extends BaseTest {

    private FlexLayoutPage flexPage;

    @BeforeEach
    public void setupContainerBuilder() {
        page.navigate("https://docs.webforj.com/webforj/flexcontainerbuilder?");
        flexPage = new FlexLayoutPage(page);

    }

    @BrowserTest
    public void testEnteringSmallValidNumberPopulatesFiveBoxes() {
        flexPage.cleanField(flexPage.getContainerBuilderInput());
        page.keyboard().type("5");
        assertThat(flexPage.getContainerBuilderContainer().locator(".demo__box")).hasCount(5);

    }

    @BrowserTest
    public void testEnteringZeroShowsValidationError() {
        flexPage.cleanField(flexPage.getContainerBuilderInput());
        page.keyboard().type("0");
        assertThat(flexPage.getContainerBuilderContainer().locator(".demo__box")).hasCount(1);
        assertThat(flexPage.getErrorMessage()).containsText("The value must be greater than or equal to 1");

    }

    @BrowserTest
    public void testEnteringNegativeOneShowsValidationError() {
        flexPage.cleanField(flexPage.getContainerBuilderInput());
        page.keyboard().type("-1");
        assertThat(flexPage.getContainerBuilderContainer().locator(".demo__box")).hasCount(1);
        assertThat(flexPage.getErrorMessage()).containsText("The value must be greater than or equal to 1");

    }

    @BrowserTest
    public void testEnteringLargeNumberPopulatesManyBoxes() {
        // Max limit is 99999999
        flexPage.cleanField(flexPage.getContainerBuilderInput());
        page.keyboard().type("9999999999", new Keyboard.TypeOptions().setDelay(300));
        assertThat(flexPage.getContainerBuilderInput()).hasValue("99999999");

    }

    @BrowserTest
    public void testEnteringSpecialCharactersShowsValidationError() {
        flexPage.cleanField(flexPage.getContainerBuilderInput());
        page.keyboard().type("Aa! #'");
        assertThat(flexPage.getContainerBuilderInput()).hasValue("");
    }
} 