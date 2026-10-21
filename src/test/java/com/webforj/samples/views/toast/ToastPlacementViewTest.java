package com.webforj.samples.views.toast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class ToastPlacementViewTest {
  @Test
  void shouldShowTheToastAtTheBottomCenterByDefault(WebforjTestbed context) {
    context.add(new ToastPlacementView());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Show Toast").locate());

    var toast = ComponentLocator.of(Toast.class).locate();
    assertEquals(Toast.Placement.BOTTOM, toast.getPlacement());
    assertEquals("This is a toast notification", toast.getText());
  }

  @ParameterizedTest
  @EnumSource(Toast.Placement.class)
  void shouldShowTheToastAtThePlacementTheUserPicks(
      Toast.Placement placement, WebforjTestbed context) {
    context.add(new ToastPlacementView());

    var choice = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.selectKey(choice, placement);
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Show Toast").locate());

    assertEquals(placement, ComponentLocator.of(Toast.class).locate().getPlacement());
  }
}
