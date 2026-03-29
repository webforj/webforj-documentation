package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.accordion.AccordionNestedPage;
import com.webforj.samples.views.BaseTest;

public class AccordionNestedViewIT extends BaseTest {

    private AccordionNestedPage accordionNestedPage;

    @BeforeEach
    public void setupAccordionNested() {
        navigateToRoute(AccordionNestedPage.getRoute());
        accordionNestedPage = new AccordionNestedPage(page);
    }

    @Test
    public void testOuterPanelStartsOpened() {
        assertThat(accordionNestedPage.getOuterPanel()).hasAttribute("opened", "");
    }

    @Test
    public void testSiblingPanelStartsClosed() {
        assertThat(accordionNestedPage.getSiblingPanel()).not().hasAttribute("opened", "");
    }

    @Test
    public void testInnerPanelsAreVisible() {
        assertThat(accordionNestedPage.getInnerPanelA()).isVisible();
        assertThat(accordionNestedPage.getInnerPanelB()).isVisible();
    }

    @Test
    public void testInnerPanelStartsClosed() {
        assertThat(accordionNestedPage.getInnerPanelA()).not().hasAttribute("opened", "");
    }

    @Test
    public void testClickingInnerPanelOpensIt() {
        accordionNestedPage.getInnerPanelA().click();
        assertThat(accordionNestedPage.getInnerPanelA()).hasAttribute("opened", "");
    }
}
