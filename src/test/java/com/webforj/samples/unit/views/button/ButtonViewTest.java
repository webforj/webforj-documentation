package com.webforj.samples.unit.views.button;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.samples.views.button.ButtonView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ButtonViewTest {
  @Test
  void shouldWelcomeTheEnteredNames(WebforjTestbed context) {
    context.add(new ButtonView());

    var fields = ComponentLocator.of(TextField.class).all();
    UserInteraction.type(fields.get(0), "Jane");
    UserInteraction.type(fields.get(1), "Doe");

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Submit").locate());

    assertEquals(1, context.getShownOptionDialogs().size());
    assertEquals("Welcome", context.getShownOptionDialogs().get(0).getTitle());
    assertEquals(
        "Welcome to the app Jane Doe!", context.getShownOptionDialogs().get(0).getMessage());
  }

  @Test
  void shouldClearAllFields(WebforjTestbed context) {
    context.add(new ButtonView());

    var fields = ComponentLocator.of(TextField.class).all();

    assertEquals(
        List.of("Jason", "Turner", "turner.jason@email.com"),
        fields.stream().map(TextField::getText).toList());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Clear").locate());
    fields.forEach(field -> assertTrue(field.getText().isEmpty()));
  }
}
