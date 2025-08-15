package com.webforj.samples.views.flexlayout.container;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.flexlayout.container.FlexDirectionPage;
import com.webforj.samples.views.BaseTest;

public class FlexDirectionIT extends BaseTest {

    private FlexDirectionPage flexDirectionPage;

    @BeforeEach
    public void setupFlexDirection() {
        navigateToRoute(FlexDirectionPage.getRoute());
        flexDirectionPage = new FlexDirectionPage(page);
    }

    @Test
    public void testFlexDirectionOptions() {
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Row").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row;.*"));

        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Row-reverse").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row-reverse;.*"));

        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Column").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column;.*"));

        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Column-reverse").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column-reverse;.*"));
    }
}
