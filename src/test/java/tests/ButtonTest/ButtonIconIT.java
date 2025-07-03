package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.APIResponse;

import pages.ButtonPage.ButtonIconPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonIconIT extends BaseTest {

    private ButtonIconPage buttonIconPage;

    @BeforeEach
    public void setupButtonIcon() {
        navigateToRoute(ButtonIconPage.getRoute());
        buttonIconPage = new ButtonIconPage(page);

    }

    @BrowserTest
    public void testNotificationsAndSearchButtonsHaveIconsAndClickable() {

        buttonIconPage.getNotificationButton().click();
        assertThat(buttonIconPage.getNotificationIcon()).isVisible();
        assertThat(buttonIconPage.getNotificationButton()).hasAttribute("has-focus", "");

        buttonIconPage.getSearchButton().click();
        assertThat(buttonIconPage.getSearchIcon()).isVisible();
        assertThat(buttonIconPage.getSearchButton()).hasAttribute("has-focus", "");

    }

    @BrowserTest
    public void testWebforjButtonImageNotBroken() {
        String imageUrl = buttonIconPage.getWebforJButton().getAttribute("src");
        assertNotNull(imageUrl, "Image src should not be null");

        APIResponse response = page.context().request().get(imageUrl);
        assertEquals(200, response.status());

    }
}