package com.webforj.samples.unit.views.lists;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ComboBox;
import com.webforj.samples.views.lists.ListSearchView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ListSearchViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ListSearchView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheSearchFieldWithItsPlaceholderAndEmptyMessage(WebforjTestbed context) {
    context.add(new ListSearchView());

    var combo = ComponentLocator.of(ComboBox.class).locate();

    assertTrue(combo.getSearch().isFieldVisible());
    assertEquals("Search countries", combo.getSearch().getPlaceholder());
    assertEquals("No countries match", combo.getSearch().getEmptyMessage());
  }
}
