package com.webforj.samples.views.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.list.ChoiceBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class DialogAlignmentsViewTest {
  @ParameterizedTest
  @EnumSource(
      value = Dialog.Alignment.class,
      names = {"TOP", "BOTTOM"})
  void shouldAlignTheDialogVerticallyAsPicked(Dialog.Alignment alignment, WebforjTestbed context) {
    context.add(new DialogAlignmentsView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.selectKey(ComponentLocator.of(ChoiceBox.class).locate(), alignment);

    assertEquals(alignment, dialog.getAlignment());
  }

  @Test
  void shouldCenterTheDialogAgain(WebforjTestbed context) {
    context.add(new DialogAlignmentsView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var alignments = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.selectKey(alignments, Dialog.Alignment.TOP);
    UserInteraction.selectKey(alignments, Dialog.Alignment.CENTER);

    assertEquals(Dialog.Alignment.CENTER, dialog.getAlignment());
  }
}
