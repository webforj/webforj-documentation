package com.webforj.samples.pages.viewtransitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.regex.Pattern;

public class ViewTransitionMorphPage {

    private static final String ROUTE = "viewtransitionmorph/";

    private final Locator blogCard;
    private final Locator blogCardTitle;
    private final Locator blogImage;
    private final Locator blogDetail;
    private final Locator blogDetailTitle;
    private final Locator blogDetailImage;
    private final Locator blogDetailClose;

    public ViewTransitionMorphPage(Page page) {
        this.blogCard = page.locator(".blog-card");
        this.blogCardTitle = page.locator(".blog-card-title");
        this.blogImage = page.locator(".blog-card .blog-image");

        this.blogDetail = page.locator(".blog-detail");
        this.blogDetailTitle = page.locator(".blog-detail-title");
        this.blogDetailImage = page.locator(".blog-detail .blog-image");
        this.blogDetailClose = page.locator(".blog-detail-close");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getBlogCard() {
        return blogCard;
    }

    public Locator getBlogCardTitle() {
        return blogCardTitle;
    }

    public Locator getBlogImage() {
        return blogImage;
    }

    public Locator getBlogDetail() {
        return blogDetail;
    }

    public Locator getBlogDetailTitle() {
        return blogDetailTitle;
    }

    public Locator getBlogDetailImage() {
        return blogDetailImage;
    }

    public Locator getBlogDetailClose() {
        return blogDetailClose;
    }

    /**
     * Helper to check if an element has the expected view-transition-name.
     * 
     * @param locator the locator to check
     * @param name    the expected transition name
     * @return the locator for chaining
     */
    public static void assertThatHasTransitionName(Locator locator,
            String name) {
        assertThat(locator)
                .hasAttribute("style", Pattern.compile("view-transition-name:\\s*" + name));
    }
}
