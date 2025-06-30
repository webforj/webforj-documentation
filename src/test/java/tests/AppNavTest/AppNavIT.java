package tests.AppNavTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import pages.AppNavPage.AppNavDemoPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class AppNavIT extends BaseTest {

    private AppNavDemoPage appNavPage;

    @BeforeEach
    public void setupAppNav() {
        navigateToRoute(AppNavDemoPage.getRoute());
        appNavPage = new AppNavDemoPage(page);
    }

    @BrowserTest
    public void testTablerIcon() {
        appNavPage.getTablerIcon().click();
        assertThat(appNavPage.getSideMenu()).not().hasAttribute("drawer-opened", "12");

        appNavPage.getTablerIcon().click();
        assertThat(appNavPage.getSideMenu()).hasAttribute("drawer-opened", "");
    }

    @BrowserTest
    public void testUpdateMainContent() {
        appNavPage.getInboxDropdown().click();

        appNavPage.getSidebarPrimaryTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Primary");

        appNavPage.getSidebarSocialTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Social");

    }

    @BrowserTest
    public void testNavigations() {
        appNavPage.getSidebarArchivedTab().click();
        assertThat(appNavPage.getMainTitle()).hasText("Application Title");
        assertThat(appNavPage.getParagraph()).containsText("Archived");

        appNavPage.getSidebarTrashTab().click();
        assertThat(appNavPage.getMainTitle()).hasText("Application Title");
        assertThat(appNavPage.getParagraph()).containsText("Trash");

    }

    @Disabled("Bug Report #307")
    @BrowserTest
    public void testAboutDropdown() {
        appNavPage.getAboutDropdown().click();
        appNavPage.getSidebarWebforJ().click();
        assertThat(page).hasURL("https://webforj.com/");
        page.goBack();
        WaitUtil.waitForVisible(appNavPage.getAboutDropdown());

        appNavPage.getAboutDropdown().click();
        appNavPage.getSidebarGitHub().click();
        assertThat(page).hasURL("https://github.com/webforj/webforj");
        page.goBack();
        WaitUtil.waitForVisible(appNavPage.getAboutDropdown());

        appNavPage.getAboutDropdown().click();
        appNavPage.getSidebarDocumentation().click();
        assertThat(page).hasURL("https://docs.webforj.com/");
        page.goBack();
        WaitUtil.waitForVisible(appNavPage.getAboutDropdown());

    }

    @BrowserTest
    public void testVisualHighlight() {
        appNavPage.getSidebarArchivedTab().click();
        assertThat(appNavPage.getSidebarArchivedTab()).hasAttribute("selected", "");

        appNavPage.getSidebarTrashTab().click();
        assertThat(appNavPage.getSidebarTrashTab()).hasAttribute("selected", "");

    }

    @BrowserTest
    public void testEndpoints() {
        appNavPage.getSidebarArchivedTab().click();
        assertThat(page).hasURL("http://localhost:8080/webforj/appnav/Archived");

        appNavPage.getSidebarTrashTab().click();
        assertThat(page).hasURL("http://localhost:8080/webforj/appnav/Trash");

    }
} 