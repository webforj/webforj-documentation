package com.webforj.samples.views.viewtransitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.webforj.samples.pages.viewtransitions.ViewTransitionMorphPage.assertThatHasTransitionName;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.config.RunConfig;
import com.webforj.samples.pages.viewtransitions.ViewTransitionMorphPage;
import com.webforj.samples.views.BaseTest;

class ViewTransitionMorphViewIT extends BaseTest {

    private ViewTransitionMorphPage morphPage;

    @BeforeEach
    void setupMorphDemo() {
        navigateToRoute(ViewTransitionMorphPage.getRoute());
        morphPage = new ViewTransitionMorphPage(page);
    }

    @Test
    void shouldMorph() {
        // Playwright 1.50 bundles Firefox ~134, which does not apply view-transition-name even
        // though webforJ sets the inline style. Verified manually in current Firefox (140+): the
        // style is set correctly. Re-enable once Playwright is upgraded to bundle Firefox 138+.
        Assumptions.assumeFalse("firefox".equalsIgnoreCase(RunConfig.getBrowser()),
            "view-transition-name not rendered by Playwright's bundled Firefox");

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
