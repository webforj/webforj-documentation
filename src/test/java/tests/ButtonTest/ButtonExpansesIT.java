package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ButtonPage.ButtonExpansesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonExpansesIT extends BaseTest {

    private ButtonExpansesPage buttonExpansesPage;

    @BeforeEach
    public void setupButtonExpanses() {
        navigateToRoute(ButtonExpansesPage.getRoute());
        buttonExpansesPage = new ButtonExpansesPage(page);

    }

    @BrowserTest
    public void testInitialButtonValueNoneAndDropdownSelectionUpdatesLabel() {
        assertThat(buttonExpansesPage.getExpanseButton()).hasText("None");

        buttonExpansesPage.getExpanseDropdown().click();
        buttonExpansesPage.getExpanseChoiceBox().getByText("XLARGE").click();

        assertThat(buttonExpansesPage.getExpanseButton()).hasText("XLARGE");
        assertThat(buttonExpansesPage.getExpanseButton()).hasAttribute("expanse", "xl");
    }
}