package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ButtonPage.ButtonExpansesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonExpansesIT extends BaseTest {

    private ButtonExpansesPage buttonPage;

    @BeforeEach
    public void setupButtonExpanses() {
        navigateToRoute(ButtonExpansesPage.getRoute());
        buttonPage = new ButtonExpansesPage(page);

    }

    @BrowserTest
    public void testInitialButtonValueNoneAndDropdownSelectionUpdatesLabel() {
        assertThat(buttonPage.getExpanseButton()).hasText("None");

        buttonPage.getExpanseDropdown().click();
        buttonPage.getExpanseChoiceBox().getByText("XLARGE").click();

        assertThat(buttonPage.getExpanseButton()).hasText("XLARGE");
        assertThat(buttonPage.getExpanseButton()).hasAttribute("expanse", "xl");
    }
} 