package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.alert.AlertExpansesPage;

import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AlertExpansesViewIT extends BaseTest {

    private AlertExpansesPage alertExpansesPage;

    Stream<String> provideRoutes() {
        return Stream.of(SupportedLanguage.values())
                .map(AlertExpansesPage::getRoute);
    }

    public void setupAlertExpanses(String route) {
        navigateToRoute(route);
        alertExpansesPage = new AlertExpansesPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testAlertExpanses(String route) {
        setupAlertExpanses(route);
        assertThat(alertExpansesPage.getAlertXSmall()).hasAttribute("expanse", "xs");
        assertThat(alertExpansesPage.getAlertMedium()).hasAttribute("expanse", "m");
        assertThat(alertExpansesPage.getAlertXLarge()).hasAttribute("expanse", "xl");
    }
}
