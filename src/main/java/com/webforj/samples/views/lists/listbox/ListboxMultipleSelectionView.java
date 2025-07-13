package com.webforj.samples.views.lists.listbox;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ListBox;
import com.webforj.component.list.MultipleSelectableList.SelectionMode;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.LIST_BOX_MULTIPLE_SELECTION)
@FrameTitle("Listbox Multiple Selection")
public class ListboxMultipleSelectionView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();
  ListBox listBox = new ListBox();
  RadioButton selectionModeToggle = RadioButton.Switch("Multiple Selection");

  public ListboxMultipleSelectionView() {
    self.setDirection(FlexDirection.COLUMN)
        .setWidth(200)
        .setMargin("20px 0 0 20px")
        .setSpacing("20px");
    self.add(listBox, selectionModeToggle);

    String[] departments = { "Marketing and Sales", "IT Support", "Management and Admin", "Finance and HR" };
    listBox.insert(departments);
    listBox.setLabel("Select Department(s)");

    selectionModeToggle.onToggle(e -> {
      if (e.isToggled()) {
        listBox.setSelectionMode(SelectionMode.MULTIPLE);
      } else {
        listBox.setSelectionMode(SelectionMode.SINGLE);
      }
    });
  }
}
