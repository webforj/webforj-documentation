package com.webforj.samples.views.drawer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.field.TextField;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class DrawerTaskViewTest {
  @Test
  void shouldAddTheTypedTaskToTheListAndClearTheField(WebforjTestbed context) {
    context.add(new DrawerTaskView());

    var drawer = ComponentLocator.of(Drawer.class).locate();
    var field = ComponentLocator.of(TextField.class).within(drawer).locate();

    UserInteraction.type(field, "Review release");
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(drawer).byText("Add Task").locate());

    assertEquals(
        List.of(
            "Finish project documentation",
            "Call John about the meeting",
            "Prepare slides for tomorrow",
            "Review release"),
        ComponentLocator.of(CheckBox.class).within(drawer).all().stream()
            .map(CheckBox::getText)
            .toList());
    assertEquals("", field.getValue());
  }

  @Test
  void shouldIgnoreABlankTask(WebforjTestbed context) {
    context.add(new DrawerTaskView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.type(ComponentLocator.of(TextField.class).within(drawer).locate(), "   ");
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(drawer).byText("Add Task").locate());

    assertEquals(3, ComponentLocator.of(CheckBox.class).within(drawer).count());
  }

  @Test
  void shouldClearOnlyTheCheckedTasks(WebforjTestbed context) {
    context.add(new DrawerTaskView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.check(
        ComponentLocator.of(CheckBox.class)
            .within(drawer)
            .byText("Call John about the meeting")
            .locate());
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(drawer).byText("Clear Completed").locate());

    assertEquals(
        List.of("Finish project documentation", "Prepare slides for tomorrow"),
        ComponentLocator.of(CheckBox.class).within(drawer).all().stream()
            .map(CheckBox::getText)
            .toList());
  }

  @Test
  void shouldDisableAddTaskAtFiftyTasksUntilACompletedTaskIsCleared(WebforjTestbed context) {
    context.add(new DrawerTaskView());

    var drawer = ComponentLocator.of(Drawer.class).locate();
    var field = ComponentLocator.of(TextField.class).within(drawer).locate();
    var add = ComponentLocator.of(Button.class).within(drawer).byText("Add Task").locate();

    for (int task = 4; task <= 50; task++) {
      UserInteraction.type(field, "Task " + task);
      UserInteraction.click(add);
    }

    assertEquals(50, ComponentLocator.of(CheckBox.class).within(drawer).count());
    assertFalse(add.isEnabled());

    UserInteraction.check(
        ComponentLocator.of(CheckBox.class).within(drawer).byText("Task 50").locate());
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(drawer).byText("Clear Completed").locate());

    assertTrue(add.isEnabled());
  }
}
