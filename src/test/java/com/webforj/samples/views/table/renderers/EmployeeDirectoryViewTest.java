package com.webforj.samples.views.table.renderers;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.CompositeRenderer;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class EmployeeDirectoryViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new EmployeeDirectoryView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRenderTheEmployeeColumnThroughACompositeRenderer(WebforjTestbed context) {
    context.add(new EmployeeDirectoryView());

    Table<Employee> table = ComponentLocator.of(Table.class).locate();

    assertInstanceOf(CompositeRenderer.class, table.getColumnById("name").getRenderer());
  }
}
