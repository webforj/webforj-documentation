package com.webforj.samples.views.columnslayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ColumnsLayoutViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ColumnsLayoutView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldKeepTheDefaultTwoColumnsWithEmailAndSubmitSpanningBoth(WebforjTestbed context) {
    context.add(new ColumnsLayoutView());

    var columns = ComponentLocator.of(ColumnsLayout.class).locate();
    var firstName = ComponentLocator.of(TextField.class).byLabel("First Name").locate();
    var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
    var submit = ComponentLocator.of(Button.class).byText("Submit").locate();

    assertEquals(ColumnsLayout.DEFAULT_BREAKPOINTS, columns.getBreakpoints());
    assertEquals(1, columns.getSpan(firstName));
    assertEquals(2, columns.getSpan(email));
    assertEquals(2, columns.getSpan(submit));
  }
}
