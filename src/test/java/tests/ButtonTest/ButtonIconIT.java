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

    private ButtonIconPage buttonPage;

    @BeforeEach
    public void setupButtonIcon() {
        navigateToRoute(ButtonIconPage.getRoute());
        buttonPage = new ButtonIconPage(page);

    }

    @BrowserTest
    public void testNotificationsAndSearchButtonsHaveIconsAndClickable() {
        
        buttonPage.getNotificationButton().click();
        assertThat(buttonPage.getNotificationIcon()).isVisible();
        assertThat(buttonPage.getNotificationButton()).hasAttribute("has-focus", "");

        buttonPage.getSearchButton().click();
        assertThat(buttonPage.getSearchIcon()).isVisible();
        assertThat(buttonPage.getSearchButton()).hasAttribute("has-focus", "");

    }

    @BrowserTest
    public void testWebforjButtonImageNotBroken() {
        String imageUrl = page.locator("dwc-button[dwc-id='15'] >>> img").getAttribute("src");
        assertNotNull(imageUrl, "Image src should not be null");

        APIResponse response = page.context().request().get(imageUrl);
        assertEquals(200, response.status());

    }
} 