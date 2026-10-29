package com.webforj.samples.unit.views.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.views.dialog.DialogThemesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class DialogThemesViewTest {
  @ParameterizedTest
  @EnumSource(value = Theme.class, mode = EnumSource.Mode.EXCLUDE, names = "SUCCESS")
  void shouldApplyThePickedTheme(Theme theme, WebforjTestbed context) {
    context.add(new DialogThemesView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.selectKey(ComponentLocator.of(ChoiceBox.class).locate(), theme);

    assertEquals(theme, dialog.getTheme());
  }

  @Test
  void shouldApplyTheSuccessThemeAgain(WebforjTestbed context) {
    context.add(new DialogThemesView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var themes = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.selectKey(themes, Theme.DANGER);
    UserInteraction.selectKey(themes, Theme.SUCCESS);

    assertEquals(Theme.SUCCESS, dialog.getTheme());
  }
}
