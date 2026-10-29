package com.webforj.samples.unit.views.fields.colorfield;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.ColorField;
import com.webforj.component.html.elements.Div;
import com.webforj.samples.views.fields.colorfield.ColorFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ColorFieldViewTest {
  @Test
  void shouldUpdateComplementaryColorsWhenColorChanges(WebforjTestbed context) {
    context.add(new ColorFieldView());

    var color = ComponentLocator.of(ColorField.class).locate();

    UserInteraction.type(color, "#0000ff");

    var panels =
        ComponentLocator.of(Div.class)
            .filter(div -> div.getStyle("background-color") != null)
            .all();
    assertEquals(
        List.of("rgb(0,0,255)", "rgb(255,0,255)", "rgb(255,0,0)", "rgb(255,255,0)"),
        panels.stream().map(div -> div.getStyle("background-color")).toList());
  }
}
