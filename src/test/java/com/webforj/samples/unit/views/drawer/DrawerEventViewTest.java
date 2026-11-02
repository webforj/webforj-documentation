package com.webforj.samples.unit.views.drawer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.event.DrawerCloseEvent;
import com.webforj.component.drawer.event.DrawerOpenEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.drawer.DrawerEventView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class DrawerEventViewTest {
  @Test
  void shouldAddTasksRejectBlankInputAndClearOnlyCompletedTasks(WebforjTestbed context) {
    context.add(new DrawerEventView());

    var field = ComponentLocator.of(TextField.class).locate();
    var add = ComponentLocator.of(Button.class).byText("Add Task").locate();

    assertEquals(3, ComponentLocator.of(CheckBox.class).all().size());

    UserInteraction.type(field, "   ");
    UserInteraction.click(add);

    assertEquals(3, ComponentLocator.of(CheckBox.class).all().size());

    UserInteraction.type(field, "Review release");
    UserInteraction.click(add);

    assertEquals("", field.getValue());
    assertEquals(4, ComponentLocator.of(CheckBox.class).all().size());

    var completed =
        ComponentLocator.of(CheckBox.class).byText("Call John about the meeting").locate();
    UserInteraction.check(completed);
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Clear Completed").locate());

    assertFalse(
        ComponentLocator.of(CheckBox.class).byText("Call John about the meeting").isPresent());
    assertTrue(ComponentLocator.of(CheckBox.class).byText("Review release").isPresent());
    assertEquals(3, ComponentLocator.of(CheckBox.class).all().size());

    for (int i = 3; i < 50; i++) {
      UserInteraction.type(field, "Task " + i);
      UserInteraction.click(add);
    }
    assertFalse(add.isEnabled());

    UserInteraction.check(ComponentLocator.of(CheckBox.class).byText("Review release").locate());
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Clear Completed").locate());

    assertTrue(add.isEnabled());
  }

  @Test
  void shouldAnnounceWhenTheDrawerClosesAndOpens(WebforjTestbed context) {
    context.add(new DrawerEventView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.close(drawer);
    UserInteraction.fireEvent(new DrawerCloseEvent(drawer, Map.of()));

    assertFalse(drawer.isOpened());
    assertTrue(ComponentLocator.of(Toast.class).byText("Drawer Closed").isPresent());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Open Task Manager").locate());
    UserInteraction.fireEvent(new DrawerOpenEvent(drawer, Map.of()));

    assertTrue(drawer.isOpened());
    assertTrue(ComponentLocator.of(Toast.class).byText("Drawer Opened").isPresent());
  }
}
