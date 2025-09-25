package com.webforj.samples.views.drawer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class DrawerTaskViewIT extends BaseTest {

    @BeforeEach
    public void setupDrawerTask() {
        navigateToRoute("drawertask");
        // Kill animations for more reliable testing
        page.addStyleTag(new Page.AddStyleTagOptions()
                .setContent("* { transition: none !important; animation: none !important; }"));
    }

    @Test
    public void testDrawerTask() {
        Locator drawer = page.locator("dwc-drawer");
        assertThat(drawer).isVisible();

        Locator meetingCheckbox = page.getByRole(AriaRole.CHECKBOX,
                new Page.GetByRoleOptions().setName("Call John about the meeting"));
        meetingCheckbox.check();

        Locator clearButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear Completed"));
        clearButton.click();

        assertThat(meetingCheckbox).not().isVisible();

        Locator newTaskInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("New Task"));
        newTaskInput.fill("New Task from IT");

        Locator addTaskButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Task"));
        addTaskButton.click();

        Locator newTaskCheckbox = page.getByRole(AriaRole.CHECKBOX,
                new Page.GetByRoleOptions().setName("New Task from IT"));
        assertThat(newTaskCheckbox).isVisible();
    }

}
