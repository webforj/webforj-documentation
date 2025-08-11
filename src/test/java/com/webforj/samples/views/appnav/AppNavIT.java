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
        appNavPage.getTablerIcon().waitFor();
        appNavPage.getTablerIcon().click();

        assertThat(appNavPage.getSideMenu()).not().hasAttribute("drawer-opened", "12");

        appNavPage.getTablerIcon().click();
        assertThat(appNavPage.getSideMenu()).hasAttribute("drawer-opened", "");
    }

    @Test
    public void testUpdateMainContent() {
        appNavPage.getInboxDropdown().waitFor();
        appNavPage.getInboxDropdown().click();

        appNavPage.getSidebarPrimaryTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Primary");

        appNavPage.getSidebarSocialTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Social");

        appNavPage.getSidebarArchivedTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Archived");

        appNavPage.getSidebarTrashTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Trash");

    }

    @Test
    public void testAboutDropdown() {
        appNavPage.getAboutDropdown().waitFor();
        appNavPage.getAboutDropdown().click();

        appNavPage.getSidebarWebforJ().waitFor();
        appNavPage.getSidebarWebforJ().click();
        assertThat(page).hasURL("https://webforj.com/");
        page.goBack();
        assertThat(appNavPage.getAboutDropdown()).isVisible();

        appNavPage.getAboutDropdown().click();
        appNavPage.getSidebarGitHub().click();
        assertThat(page).hasURL("https://github.com/webforj/webforj");
        page.goBack();
        assertThat(appNavPage.getAboutDropdown()).isVisible();

        appNavPage.getAboutDropdown().click();
        appNavPage.getSidebarDocumentation().click();
        assertThat(page).hasURL("https://docs.webforj.com/");
        page.goBack();
        assertThat(appNavPage.getAboutDropdown()).isVisible();

    }

    @Test
    public void testVisualHighlight() {
        appNavPage.getSidebarArchivedTab().waitFor();

        appNavPage.getSidebarArchivedTab().click();
        assertThat(appNavPage.getSidebarArchivedTab()).hasAttribute("selected", "");

        appNavPage.getSidebarTrashTab().click();
        assertThat(appNavPage.getSidebarTrashTab()).hasAttribute("selected", "");

    }
}
