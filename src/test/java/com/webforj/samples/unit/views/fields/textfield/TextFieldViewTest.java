package com.webforj.samples.unit.views.fields.textfield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.samples.views.fields.textfield.TextFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TextFieldViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TextFieldView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldGiveEachFieldItsOwnType(WebforjTestbed context) {
    context.add(new TextFieldView());

    var fields = ComponentLocator.of(TextField.class).all();

    assertEquals(
        List.of(
            TextField.Type.TEXT,
            TextField.Type.EMAIL,
            TextField.Type.TEL,
            TextField.Type.URL,
            TextField.Type.SEARCH),
        fields.stream().map(TextField::getType).toList());
  }
}
