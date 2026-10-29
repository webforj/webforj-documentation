package com.webforj.samples.unit.views.lists.listbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ListBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.lists.listbox.ListboxMultipleSelectionView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ListboxMultipleSelectionViewTest {
  @Test
  void shouldSelectOneDepartmentAtATimeByDefault(WebforjTestbed context) {
    context.add(new ListboxMultipleSelectionView());

    var list = ComponentLocator.of(ListBox.class).locate();

    UserInteraction.selectIndex(list, 0);
    UserInteraction.selectIndex(list, 1);

    assertEquals(List.of(1), list.getSelectedIndices());
  }

  @Test
  void shouldSelectSeveralDepartmentsOnceMultipleSelectionIsOn(WebforjTestbed context) {
    context.add(new ListboxMultipleSelectionView());

    var list = ComponentLocator.of(ListBox.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();

    UserInteraction.check(toggle);
    UserInteraction.selectIndex(list, 0);
    UserInteraction.selectIndex(list, 1);

    assertEquals(List.of(0, 1), list.getSelectedIndices());
  }

  @Test
  void shouldReturnToOneDepartmentAtATimeOnceMultipleSelectionIsOff(WebforjTestbed context) {
    context.add(new ListboxMultipleSelectionView());

    var list = ComponentLocator.of(ListBox.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();

    UserInteraction.check(toggle);
    UserInteraction.selectIndex(list, 0);
    UserInteraction.selectIndex(list, 1);
    UserInteraction.uncheck(toggle);
    UserInteraction.selectIndex(list, 2);

    assertEquals(List.of(2), list.getSelectedIndices());
  }
}
