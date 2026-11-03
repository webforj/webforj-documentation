package com.webforj.samples.unit.views.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.navigator.Navigator;
import com.webforj.samples.views.navigator.NavigatorLayoutView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class NavigatorLayoutViewTest {
  @Test
  void shouldStartWithThePreviewLayout(WebforjTestbed context) {
    context.add(new NavigatorLayoutView());

    var nav = ComponentLocator.of(Navigator.class).locate();

    assertEquals(Navigator.Layout.PREVIEW, nav.getLayout());
  }

  @ParameterizedTest
  @CsvSource({"0, NONE", "1, PAGES", "3, QUICK_JUMP"})
  void shouldSwitchToTheLayoutTheUserPicks(
      int index, Navigator.Layout layout, WebforjTestbed context) {
    context.add(new NavigatorLayoutView());

    var nav = ComponentLocator.of(Navigator.class).locate();
    var choices = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.selectIndex(choices, index);

    assertEquals(layout, nav.getLayout());
  }
}
