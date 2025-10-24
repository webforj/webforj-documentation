package com.webforj.samples.views.alert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.views.BaseTest;

public class AlertThemesViewIT extends BaseTest {

    private AlertThemesPage alertThemesPage;

    @BeforeEach
    public void setupAlertThemes() {
        navigateToRoute(AlertThemesPage.getRoute());
        alertThemesPage = new AlertThemesPage(page);
    }

    @Test
    public void testAlertThemes() {
        assertThat(alertThemesPage.getSuccessAlert()).hasAttribute("theme", "success");
    }

}
