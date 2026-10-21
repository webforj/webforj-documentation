package com.webforj.samples.views.lists.combobox;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ComboBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ComboBoxPlaceholderViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ComboBoxPlaceholderView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldPromptWithThePlaceholderWhileNothingIsEntered(WebforjTestbed context) {
    context.add(new ComboBoxPlaceholderView());

    var combo = ComponentLocator.of(ComboBox.class).locate();

    verifyThat(combo).hasText("").hasPlaceholder("Example Placeholder");
  }
}
