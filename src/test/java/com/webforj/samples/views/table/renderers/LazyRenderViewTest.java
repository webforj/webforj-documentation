package com.webforj.samples.views.table.renderers;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class LazyRenderViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new LazyRenderView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRenderOnlyTheUsageColumnsLazily(WebforjTestbed context) {
    context.add(new LazyRenderView());

    Table<Server> table = ComponentLocator.of(Table.class).locate();

    assertEquals(
        List.of("cpuUsage", "memoryUsage", "diskUsage"),
        table.getColumns().stream().filter(Column::isLazyRender).map(Column::getId).toList());
  }
}
