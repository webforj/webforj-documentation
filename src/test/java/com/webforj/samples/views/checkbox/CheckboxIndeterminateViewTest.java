package com.webforj.samples.views.checkbox;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class CheckboxIndeterminateViewTest {
  @Test
  void shouldShowTheParentIndeterminateWhileOnlySomeChildrenAreChecked(WebforjTestbed context) {
    context.add(new CheckboxIndeterminateView());

    var parent = ComponentLocator.of(CheckBox.class).byText("Parent").locate();
    var second = ComponentLocator.of(CheckBox.class).byText("Child 2").locate();

    assertTrue(parent.isIndeterminate());

    UserInteraction.uncheck(second);

    assertFalse(parent.isIndeterminate());
    assertFalse(parent.isChecked());
  }

  @Test
  void shouldCheckTheParentOnceEveryChildIsChecked(WebforjTestbed context) {
    context.add(new CheckboxIndeterminateView());

    var parent = ComponentLocator.of(CheckBox.class).byText("Parent").locate();

    UserInteraction.check(ComponentLocator.of(CheckBox.class).byText("Child 1").locate());

    assertTrue(parent.isChecked());
    assertFalse(parent.isIndeterminate());
  }

  @Test
  void shouldCheckAndUncheckEveryChildFromTheParent(WebforjTestbed context) {
    context.add(new CheckboxIndeterminateView());

    var parent = ComponentLocator.of(CheckBox.class).byText("Parent").locate();
    var first = ComponentLocator.of(CheckBox.class).byText("Child 1").locate();
    var second = ComponentLocator.of(CheckBox.class).byText("Child 2").locate();

    UserInteraction.check(parent);

    assertTrue(first.isChecked());
    assertTrue(second.isChecked());

    UserInteraction.uncheck(parent);

    assertFalse(first.isChecked());
    assertFalse(second.isChecked());
  }
}
