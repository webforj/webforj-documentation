package com.webforj.samples.views.viewtransitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.webforj.samples.pages.viewtransitions.ViewTransitionMorphPage.assertThatHasTransitionName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.viewtransitions.ViewTransitionMorphPage;
import com.webforj.samples.views.BaseTest;

/**
 * Integration tests for the View Transition Morph demo using Page Object Model.
 *
 * @author Hyyan Abou Assali
 */
class ViewTransitionMorphViewIT extends BaseTest {

    private ViewTransitionMorphPage morphPage;

    @BeforeEach
    void setupMorphDemo() {
        navigateToRoute(ViewTransitionMorphPage.getRoute());
        morphPage = new ViewTransitionMorphPage(page);
    }

    @Test
    void shouldMorph() {

        assertThat(morphPage.getBlogCard()).isVisible();
        assertThat(morphPage.getBlogCardTitle()).hasText("The Art of Writing");

        assertThatHasTransitionName(morphPage.getBlogImage(), "blog-image");

        morphPage.getBlogCard().click();

        assertThat(morphPage.getBlogDetail()).isVisible();
        assertThat(morphPage.getBlogDetailTitle()).hasText("The Art of Writing");

        assertThatHasTransitionName(morphPage.getBlogDetailImage(), "blog-image");

        morphPage.getBlogDetailClose().click();

        assertThat(morphPage.getBlogCard()).isVisible();
    }
}
