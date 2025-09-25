package com.webforj.samples.views.lists.choicebox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class ChoiceboxDropdownTypeViewIT extends BaseTest {

    @BeforeEach
    public void setupChoiceboxDropdownType() {
        navigateToRoute("choiceboxdropdowntype");
    }

    @Test
    public void testChoiceboxDropdownType() {

        Locator choiceBoxElectronics = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Electronics"));
        choiceBoxElectronics.click();

        Locator kitchenDropdown = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Kitchen"));
        kitchenDropdown.click();

        Locator choiceBoxKitchen = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Kitchen"));

        assertThat(choiceBoxKitchen).isVisible();
    }

}
