package com.webforj.samples.views.appnav;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.appnav.AppNavPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppNavViewIT extends BaseTest {

    private AppNavPage appNavPage;

    public void setupAppNav(SupportedLanguage language) {
        navigateToRoute(AppNavPage.getRoute(language));
        appNavPage = new AppNavPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDrawerOpensAndCloses(SupportedLanguage language) {
        setupAppNav(language);
        appNavPage.getTablerIcon().click();

        assertThat(appNavPage.getAppLayout()).not().hasAttribute("drawer-opened", "12");

        appNavPage.getTablerIcon().click();
        assertThat(appNavPage.getAppLayout()).hasAttribute("drawer-opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPrimaryTabSelected(SupportedLanguage language) {
        setupAppNav(language);
        appNavPage.getInboxDropdown().click();

        appNavPage.getSidebarPrimaryTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Primary");

    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testExternalLinksAreCorrect(SupportedLanguage language) {
        setupAppNav(language);
        appNavPage.getAboutDropdown().click();

        assertThat(appNavPage.getSidebarWebforJ().locator("a")).hasAttribute("href", "https://webforj.com/");
        assertThat(appNavPage.getSidebarGitHub().locator("a")).hasAttribute("href",
                "https://github.com/webforj/webforj");
        assertThat(appNavPage.getSidebarDocumentation().locator("a")).hasAttribute("href",
                "https://documentation.webforj.com/");

    }
}
