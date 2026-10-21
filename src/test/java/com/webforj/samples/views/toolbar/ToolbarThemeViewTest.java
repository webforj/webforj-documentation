package com.webforj.samples.views.toolbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ToolbarThemeViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ToolbarThemeView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowOneToolbarForEachOfTheSevenBuiltInThemes(WebforjTestbed context) {
    context.add(new ToolbarThemeView());

    var toolbars = ComponentLocator.of(Toolbar.class).all();

    assertEquals(
        List.of(
            Theme.DANGER,
            Theme.DEFAULT,
            Theme.GRAY,
            Theme.INFO,
            Theme.PRIMARY,
            Theme.SUCCESS,
            Theme.WARNING),
        toolbars.stream().map(Toolbar::getTheme).toList());
  }
}
