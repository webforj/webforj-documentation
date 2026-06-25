package com.webforj.samples.views.lists.choicebox;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/lists/combobox/comboBoxDropDownType.css")
@Route
@FrameTitle("Choicebox Dropdown Type")
public class ChoiceboxDropdownTypeView extends Composite<FlexLayout> {
  // Categories for the ChoiceBox
  private static final String[] CATEGORIES = {
    "Electronics",
    "Health and Beauty",
    "Fashion",
    "Kitchen",
    "Furniture",
    "Pet Supplies",
    "Toys and Games"
  };

  private final FlexLayout self = getBoundComponent();
  // ChoiceBox component with custom dropdown styling
  private final ChoiceBox demoBox = new ChoiceBox("Department");

  public ChoiceboxDropdownTypeView() {
    self.setWidth("200px").setMargin("20px").add(demoBox);

    demoBox.setDropdownType("demo-dropdown-type");
    demoBox.insert(CATEGORIES).selectIndex(0);
  }
}
