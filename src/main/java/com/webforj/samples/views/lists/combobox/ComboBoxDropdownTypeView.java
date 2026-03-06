package com.webforj.samples.views.lists.combobox;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/lists/combobox/comboBoxDropDownType.css")
@Route
@FrameTitle("ComboBox Dropdown Type")
public class ComboBoxDropdownTypeView extends Composite<Div> {
  // Categories for the ComboBox
  private static final String[] CATEGORIES = {
      "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
      "Pet Supplies", "Toys and Games"
  };

  private final Div self = getBoundComponent();
  // ComboBox component with custom dropdown styling
  private final ComboBox comboBox = new ComboBox("Department");

  public ComboBoxDropdownTypeView() {
    // Configure main container
    self.addClassName("frame")
        .add(comboBox);

    // Set custom dropdown type for styling and insert categories
    comboBox.setDropdownType("demo-dropdown-type")
        .insert(CATEGORIES)
        .selectIndex(0);
  }
}
