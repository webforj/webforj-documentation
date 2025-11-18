package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.alert.AlertExpansesPage;

import com.webforj.samples.views.BaseTest;

public class AlertExpansesViewIT extends BaseTest {

    private AlertExpansesPage alertExpansesPage;

    @BeforeEach
    public void setupAlertExpanses() {
        navigateToRoute(AlertExpansesPage.getRoute());
        alertExpansesPage = new AlertExpansesPage(page);
    }

    @Test
    public void testAlertExpanses() {

        assertThat(alertExpansesPage.getAlertXSmall()).hasAttribute("expanse", "xs");
        assertThat(alertExpansesPage.getAlertMedium()).hasAttribute("expanse", "m");
        assertThat(alertExpansesPage.getAlertXLarge()).hasAttribute("expanse", "xl");
    }
}
