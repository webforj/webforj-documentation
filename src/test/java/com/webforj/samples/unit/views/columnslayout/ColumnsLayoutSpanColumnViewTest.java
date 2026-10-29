package com.webforj.samples.unit.views.columnslayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.samples.views.columnslayout.ColumnsLayoutSpanColumnView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ColumnsLayoutSpanColumnViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ColumnsLayoutSpanColumnView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldSpanTheEmailFieldAcrossTwoColumnsOnlyAtTheMediumBreakpoint(WebforjTestbed context) {
    context.add(new ColumnsLayoutSpanColumnView());

    var columns = ComponentLocator.of(ColumnsLayout.class).locate();
    var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();

    assertEquals(2, columns.getSpan(email, "medium"));
    assertEquals(1, columns.getSpan(email, "large"));
    assertEquals(1, columns.getSpan(email));
  }
}
