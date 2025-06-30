package tests.RadioButtonGroup;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.RadioButtonGroupPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonIT extends BaseTest {

    private RadioButtonGroupPage radioButtonGroupPage;

    @BeforeEach
    public void setupRadioButtonGroup() {
        page.navigate("https://docs.webforj.com/webforj/radiobuttongroup?");
        radioButtonGroupPage = new RadioButtonGroupPage(page);
    }

    @BrowserTest
    public void testRadioButtonGroup() {
        radioButtonGroupPage.getStronglyDisagreeRB().click();
        assertThat(radioButtonGroupPage.getStronglyDisagreeRB()).isChecked();

        radioButtonGroupPage.getDisagreeRB().click();
        assertThat(radioButtonGroupPage.getDisagreeRB()).isChecked();
        assertThat(radioButtonGroupPage.getStronglyDisagreeRB()).not().isChecked();

        radioButtonGroupPage.getNeutralRB().click();
        assertThat(radioButtonGroupPage.getNeutralRB()).isChecked();
        assertThat(radioButtonGroupPage.getDisagreeRB()).not().isChecked();

        radioButtonGroupPage.getAgreeRB().click();
        assertThat(radioButtonGroupPage.getAgreeRB()).isChecked();
        assertThat(radioButtonGroupPage.getNeutralRB()).not().isChecked();

        radioButtonGroupPage.getStronglyAgreeRB().click();
        assertThat(radioButtonGroupPage.getStronglyAgreeRB()).isChecked();
        assertThat(radioButtonGroupPage.getAgreeRB()).not().isChecked();

    }
} 