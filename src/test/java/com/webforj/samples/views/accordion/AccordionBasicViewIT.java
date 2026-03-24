package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.accordion.AccordionBasicPage;
import com.webforj.samples.views.BaseTest;

public class AccordionBasicViewIT extends BaseTest {

    private AccordionBasicPage accordionBasicPage;

    @BeforeEach
    public void setupAccordionBasic() {
        navigateToRoute(AccordionBasicPage.getRoute());
        accordionBasicPage = new AccordionBasicPage(page);
    }

    @Test
    public void testSectionOneStartsOpened() {
        assertThat(accordionBasicPage.getSectionOne()).hasAttribute("opened", "");
    }

    @Test
    public void testSectionTwoStartsClosed() {
        assertThat(accordionBasicPage.getSectionTwo()).not().hasAttribute("opened", "");
    }

    @Test
    public void testSectionThreeIsVisible() {
        assertThat(accordionBasicPage.getSectionThree()).isVisible();
    }

    @Test
    public void testClickingSectionTwoOpensIt() {
        accordionBasicPage.getSectionTwo().click();
        assertThat(accordionBasicPage.getSectionTwo()).hasAttribute("opened", "");
    }
}
