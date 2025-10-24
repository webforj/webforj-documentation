package com.webforj.samples.views.composite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.composite.CompositePage;
import com.webforj.samples.views.BaseTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CompositeViewIT extends BaseTest {

    private CompositePage compositePage;

    @BeforeEach
    public void setupComposite() {
        navigateToRoute(CompositePage.getRoute());
        compositePage = new CompositePage(page);
    }

    @Test
    public void testAddItemWhenEnterKeyIsPressed() {
        compositePage.getToDoInput().fill("New Task");
        compositePage.getToDoInput().press("Enter");
        Locator addedItem = page.getByText("New Task");
        assertThat(addedItem).isVisible();
    }

    @Test
    public void testDeleteItemWhenDeleteButtonIsClicked() {
        compositePage.getFirstDeleteButton().click();
        assertThat(compositePage.getFirstTaskCheckbox()).not().isVisible();
    }

    @Test
    public void testBlankInputDoesNotAddItem() {
        compositePage.getToDoInput().fill(" ");
        compositePage.getToDoInput().press("Enter");
        Locator radioButtons = page.getByRole(AriaRole.RADIO);
        assertThat(radioButtons.nth(3)).not().isVisible();
    }
}
