package com.webforj.samples.views.table.renderers;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class InvoiceListViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new InvoiceListView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldPickTheStatusBadgeThroughAConditionalRenderer(WebforjTestbed context) {
    context.add(new InvoiceListView());

    Table<Invoice> table = ComponentLocator.of(Table.class).locate();

    assertInstanceOf(ConditionalRenderer.class, table.getColumnById("status").getRenderer());
  }
}
