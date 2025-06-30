package tests.RadioButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.RadioButtonPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonGroupIT extends BaseTest {

    private RadioButtonPage radioButton;

    @BeforeEach
    public void setupRadioButtonGroup() {
        page.navigate("https://docs.webforj.com/webforj/radiobuttongroup?");
        radioButton = new RadioButtonPage(page);
    }

    @BrowserTest
    public void testButtonVisibility() {
        radioButton.getStronglyDisagreeInput().click();
        assertThat(radioButton.getStronglyDisagreeInput()).hasAttribute("aria-checked", "true");

        Locator agreeButton = page.locator("dwc-radio[dwc-id=\"16\"] >> input");
        agreeButton.click();
        assertThat(agreeButton).hasAttribute("aria-checked", "true");

    }

    @BrowserTest
    public void testButtonRealibility() {
        radioButton.getStronglyDisagreeInput().click();

        int width = page.viewportSize().width;
        int height = page.viewportSize().height;

        int centerX = width / 2;
        int centerY = height / 2;

        page.mouse().click(centerX, centerY);

        assertThat(radioButton.getStronglyDisagreeInput()).hasAttribute("aria-checked", "true");

    }
} 