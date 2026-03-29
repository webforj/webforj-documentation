package com.webforj.samples.views.viewtransitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.viewtransitions.ViewTransitionEnterExitPage;
import com.webforj.samples.views.BaseTest;

class ViewTransitionEnterExitViewIT extends BaseTest {

    private ViewTransitionEnterExitPage enterExitPage;

    @BeforeEach
    void setupEnterExitDemo() {
        navigateToRoute(ViewTransitionEnterExitPage.getRoute());
        enterExitPage = new ViewTransitionEnterExitPage(page);
    }

    @Test
    @DisplayName("Should toggle notification check visibility")
    void shouldToggleNotification() {
        assertThat(enterExitPage.getNotificationCard()).isVisible();
        assertThat(enterExitPage.getNotificationTitle()).hasText("Success!");
        assertThat(enterExitPage.getNotificationMessage()).hasText("Your changes have been saved successfully.");

        enterExitPage.getNotificationDismiss().click();
        assertThat(enterExitPage.getNotificationCard()).isHidden();

        enterExitPage.getTriggerBtn().click();
        assertThat(enterExitPage.getNotificationCard()).isVisible();

        enterExitPage.getTriggerBtn().click();
        assertThat(enterExitPage.getNotificationCard()).isHidden();
    }
}
