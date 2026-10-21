package com.webforj.samples.views.columnslayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ColumnsLayoutAlignmentViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ColumnsLayoutAlignmentView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldAlignSubmitToTheEndOfTheSecondColumn(WebforjTestbed context) {
    context.add(new ColumnsLayoutAlignmentView());

    var columns = ComponentLocator.of(ColumnsLayout.class).locate();
    var submit = ComponentLocator.of(Button.class).byText("Submit").locate();

    assertEquals(2, columns.getColumn(submit));
    assertEquals(ColumnsLayout.Alignment.END, columns.getHorizontalAlignment(submit));
  }
}
