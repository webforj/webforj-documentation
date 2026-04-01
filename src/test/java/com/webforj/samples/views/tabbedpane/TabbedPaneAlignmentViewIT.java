package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPaneAlignmentPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TabbedPaneAlignmentViewIT extends BaseTest {

    private TabbedPaneAlignmentPage tabbedPaneAlignmentPage;

    public void setupAlignmentTabbedPane(SupportedLanguage language) {
        navigateToRoute(TabbedPaneAlignmentPage.getRoute(language));
        tabbedPaneAlignmentPage = new TabbedPaneAlignmentPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testAlignmentTabbedPane(SupportedLanguage language) {
      setupAlignmentTabbedPane(language);
        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentDropdownButton().click(); //CENTER
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "center");
    }
}
