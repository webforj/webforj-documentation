package com.webforj.samples.unit.views.columnslayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.samples.views.columnslayout.ColumnsLayoutFormView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ColumnsLayoutFormViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ColumnsLayoutFormView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldSpanTheEmailAndAddressRowsAcrossBothColumns(WebforjTestbed context) {
    context.add(new ColumnsLayoutFormView());

    var columns = ComponentLocator.of(ColumnsLayout.class).locate();
    var zip = ComponentLocator.of(TextField.class).byLabel("Zip").locate();
    var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
    var address = ComponentLocator.of(TextField.class).byLabel("Address").locate();

    assertEquals(1, columns.getSpan(zip));
    assertEquals(2, columns.getSpan(email));
    assertEquals(2, columns.getSpan(address));
  }
}
