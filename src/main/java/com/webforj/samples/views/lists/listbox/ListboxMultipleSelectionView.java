package com.webforj.samples.views.lists.listbox;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ListBox;
import com.webforj.component.list.MultipleSelectableList.SelectionMode;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Listbox Multiple Selection")
public class ListboxMultipleSelectionView extends Composite<FlexLayout> {
  // Departments for the ListBox
  private static final String[] DEPARTMENTS = {
      "Marketing and Sales", "IT Support", "Management and Admin", "Finance and HR"
  };

  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final ListBox listBox = new ListBox();
  private final RadioButton selectionModeToggle = RadioButton.Switch("Multiple Selection");

  public ListboxMultipleSelectionView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.COLUMN)
        .setWidth(200)
        .setMargin("20px 0 0 20px")
        .setSpacing("20px");

    // Configure ListBox with departments and label
    listBox.insert(DEPARTMENTS)
        .setLabel("Select Department(s)");

    // Toggle between single and multiple selection mode
    selectionModeToggle.onToggle(e -> {
      if (e.isToggled()) {
        listBox.setSelectionMode(SelectionMode.MULTIPLE);
      } else {
        listBox.setSelectionMode(SelectionMode.SINGLE);
      }
    });

    self.add(listBox, selectionModeToggle);
  }
}
