package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage.TabbedPaneExpanseThemePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TabbedPaneExpanseThemeIT extends BaseTest {

    private TabbedPaneExpanseThemePage tabbedPaneExpanseThemePage;

    @BeforeEach
    public void setupExpansesAndThemes() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpaneexpansetheme?");
        tabbedPaneExpanseThemePage = new TabbedPaneExpanseThemePage(page);
    }

    @BrowserTest
    public void testThemes() {
        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=DANGER").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "danger");

        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=DEFAULT").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "default");

        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=GRAY").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "gray");

        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=INFO").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "info");

        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=PRIMARY").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "primary");

        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=SUCCESS").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "success");

        tabbedPaneExpanseThemePage.getThemesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=WARNING").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "warning");

    }

    @BrowserTest
    public void testExpanses() {
        tabbedPaneExpanseThemePage.getExpansesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=XLARGE").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "xl");

        tabbedPaneExpanseThemePage.getExpansesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator(":text-is('LARGE')").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "l");

        tabbedPaneExpanseThemePage.getExpansesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=MEDIUM").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "m");

        tabbedPaneExpanseThemePage.getExpansesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator(":text-is('SMALL')").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "s");

        tabbedPaneExpanseThemePage.getExpansesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=XSMALL").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "xs");

        tabbedPaneExpanseThemePage.getExpansesDropdown().click();
        tabbedPaneExpanseThemePage.getExpanseThemeListBox().locator("text=NONE").click();
        assertThat(tabbedPaneExpanseThemePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "");
    }
}