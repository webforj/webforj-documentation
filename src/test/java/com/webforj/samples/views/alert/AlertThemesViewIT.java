package com.webforj.samples.views.alert;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertExpansesPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AlertThemesViewIT extends BaseTest {

    private AlertThemesPage alertThemesPage;

    Stream<String> provideRoutes() {
        return Stream.of(SupportedLanguage.values())
                .map(AlertThemesPage::getRoute);
    }

    public void setupAlertThemes(String route) {
        navigateToRoute(route);
        alertThemesPage = new AlertThemesPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testAlertThemes(String route) {
        setupAlertThemes(route);
        assertThat(alertThemesPage.getSuccessAlert()).hasAttribute("theme", "success");
    }

}
