package tests.appnav;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.appnav.AppNavPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class AppNavIT extends BaseTest {

    private AppNavPage appNavPage;

    @BeforeEach
    public void setupAppNav() {
        navigateToRoute(AppNavPage.getRoute());
        appNavPage = new AppNavPage(page);
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

        appNavPage.getSidebarArchivedTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Archived");

        appNavPage.getSidebarTrashTab().click();
        assertThat(appNavPage.getParagraph()).containsText("Trash");

    }

    @BrowserTest
    public void testAboutDropdown() {
        appNavPage.getAboutDropdown().click();
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

    @BrowserTest
    public void testVisualHighlight() {
        appNavPage.getSidebarArchivedTab().click();
        assertThat(appNavPage.getSidebarArchivedTab()).hasAttribute("selected", "");

        appNavPage.getSidebarTrashTab().click();
        assertThat(appNavPage.getSidebarTrashTab()).hasAttribute("selected", "");

    }

    // @BrowserTest
    // public void testEndpoints() {
    //     appNavPage.getSidebarArchivedTab().click();
    //     assertThat(page).hasURL("http://localhost:8080/webforj/appnav/Archived");

    //     appNavPage.getSidebarTrashTab().click();
    //     assertThat(page).hasURL("http://localhost:8080/webforj/appnav/Trash");

    // }
}