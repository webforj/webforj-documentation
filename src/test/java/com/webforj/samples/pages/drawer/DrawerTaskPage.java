package com.webforj.samples.pages.drawer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DrawerTaskPage {

    private static final String ROUTE = "drawertask";

    private final Locator drawer;
    private final Locator meetingCheckbox;
    private final Locator clearButton;
    private final Locator newTaskInput;
    private final Locator addTaskButton;
    private final Locator newTaskCheckbox;

    public DrawerTaskPage(Page page) {
        this.drawer = page.locator("dwc-drawer");
        this.meetingCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Call John about the meeting"));
        this.clearButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear Completed"));
        this.newTaskInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("New Task"));
        this.addTaskButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Task"));
        this.newTaskCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("New Task from IT"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDrawer() {
        return drawer;
    }

    public Locator getMeetingCheckbox() {
        return meetingCheckbox;
    }

    public Locator getClearButton() {
        return clearButton;
    }

    public Locator getNewTaskInput() {
        return newTaskInput;
    }

    public Locator getAddTaskButton() {
        return addTaskButton;
    }

    public Locator getNewTaskCheckbox() {
        return newTaskCheckbox;
    }
}
