package com.webforj.samples.views.drawer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.drawer.DrawerTaskPage;
import com.webforj.samples.views.BaseTest;

public class DrawerTaskViewIT extends BaseTest {

    private DrawerTaskPage drawerTaskPage;

    @BeforeEach
    public void setupDrawerTask() {
        navigateToRoute(DrawerTaskPage.getRoute());
        drawerTaskPage = new DrawerTaskPage(page);
    }

    @Test
    public void testTaskAddedWhenAddTaskButtonIsClicked() {
        assertThat(drawerTaskPage.getDrawer()).isVisible();
        drawerTaskPage.getMeetingCheckbox().check();
        drawerTaskPage.getClearButton().click();
        assertThat(drawerTaskPage.getMeetingCheckbox()).not().isVisible();
        drawerTaskPage.getNewTaskInput().fill("New Task from IT");
        drawerTaskPage.getAddTaskButton().click();
        assertThat(drawerTaskPage.getNewTaskCheckbox()).isVisible();
    }

}
