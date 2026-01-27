package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.pages.alert.ClosableAlertPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ClosableAlertViewIT extends BaseTest {


    public void setupClosableAlert(SupportedLanguage language) {
        navigateToRoute(ClosableAlertPage.getRoute(language));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testAlertClosesWhenCloseButtonIsClicked(SupportedLanguage language) {
        setupClosableAlert(language);
        Locator alert = page.getByRole(AriaRole.ALERT);

        assertThat(alert).isVisible();

        Locator closeButton = page.getByRole(AriaRole.BUTTON).filter().getByLabel("icon x");
        closeButton.click();
        assertThat(alert).not().isVisible();
    }
}
