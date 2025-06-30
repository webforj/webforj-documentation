package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ExpansesAndThemesIT extends BaseTest {

    private TabbedPanePage tabbedPanePage;

    @BeforeEach
    public void setupExpansesAndThemes() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpaneexpansetheme?");
        tabbedPanePage = new TabbedPanePage(page);
    }

    @BrowserTest
    public void testThemes() {
        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=DANGER").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "danger");

        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=DEFAULT").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "default");

        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=GRAY").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "gray");

        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=INFO").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "info");

        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=PRIMARY").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "primary");

        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=SUCCESS").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "success");

        tabbedPanePage.getThemesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=WARNING").click();
        assertThat(tabbedPanePage.getExpanseThemeDashboardTab()).hasAttribute("theme", "warning");

    }

    @BrowserTest
    public void testExpanses() {
        tabbedPanePage.getExpansesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=XLARGE").click();
        assertThat(tabbedPanePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "xl");

        tabbedPanePage.getExpansesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator(":text-is('LARGE')").click();
        assertThat(tabbedPanePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "l");

        tabbedPanePage.getExpansesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=MEDIUM").click();
        assertThat(tabbedPanePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "m");

        tabbedPanePage.getExpansesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator(":text-is('SMALL')").click();
        assertThat(tabbedPanePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "s");

        tabbedPanePage.getExpansesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=XSMALL").click();
        assertThat(tabbedPanePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "xs");

        tabbedPanePage.getExpansesDropdown().click();
        tabbedPanePage.getExpanseThemeListBox().locator("text=NONE").click();
        assertThat(tabbedPanePage.getExpanseThemeTabbedPane()).hasAttribute("expanse", "");
    }
} 