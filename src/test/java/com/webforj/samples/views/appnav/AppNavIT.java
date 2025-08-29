package com.webforj.samples.views.appnav;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.appnav.AppNavPage;
import com.webforj.samples.views.BaseTest;

public class AppNavIT extends BaseTest {

    private AppNavPage appNavPage;

    @BeforeEach
    public void setupAppNav() {
        navigateToRoute(AppNavPage.getRoute());
        appNavPage = new AppNavPage(page);
    }

    @Test
    public void testTablerIcon() {
        appNavPage.getTablerIcon().click();

        assertThat(appNavPage.getAppLayout()).not().hasAttribute("drawer-opened", "12");

        appNavPage.getTablerIcon().click();
        assertThat(appNavPage.getAppLayout()).hasAttribute("drawer-opened", "");
    }

    @Test
    public void testUpdateMainContent() {
        appNavPage.getInboxDropdown().click();

        appNavPage.getSidebarPrimaryTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Primary");

    }

    @Test
    public void testAboutDropdown() {
        appNavPage.getAboutDropdown().click();

        assertThat(appNavPage.getSidebarWebforJ().locator("a")).hasAttribute("href", "https://webforj.com/");
        assertThat(appNavPage.getSidebarGitHub().locator("a")).hasAttribute("href",
                "https://github.com/webforj/webforj");
        assertThat(appNavPage.getSidebarDocumentation().locator("a")).hasAttribute("href",
                "https://documentation.webforj.com/");

    }
}
