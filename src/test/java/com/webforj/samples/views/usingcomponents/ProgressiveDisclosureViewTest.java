package com.webforj.samples.views.usingcomponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Fieldset;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ProgressiveDisclosureViewTest {
  @Test
  void shouldRevealAdvancedSettingsAndResetSaveAfterChanges(WebforjTestbed context) {
    context.add(new ProgressiveDisclosureView());

    var advanced = ComponentLocator.of(Fieldset.class).locate();
    var toggle = ComponentLocator.of(Button.class).byText("Show advanced settings").locate();
    var save = ComponentLocator.of(Button.class).byText("Save Settings").locate();

    assertEquals("none", advanced.getStyle("display"));
    assertFalse(save.isEnabled());

    UserInteraction.click(toggle);

    assertEquals("grid", advanced.getStyle("display"));
    assertEquals("Hide advanced settings", toggle.getText());

    UserInteraction.check(ComponentLocator.of(CheckBox.class).byText("Debug mode").locate());
    assertTrue(save.isEnabled());

    UserInteraction.click(save);

    assertFalse(save.isEnabled());
    assertEquals(
        "Settings saved successfully", ComponentLocator.of(Toast.class).locate().getText());

    UserInteraction.click(toggle);
    assertEquals("none", advanced.getStyle("display"));

    UserInteraction.uncheck(
        ComponentLocator.of(CheckBox.class).byText("Email notifications").locate());
    assertTrue(save.isEnabled());
  }
}
