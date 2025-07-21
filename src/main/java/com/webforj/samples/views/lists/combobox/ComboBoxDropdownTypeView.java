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

  ComboBox comboBox = new ComboBox("Department");

  public ComboBoxDropdownTypeView() {
    getBoundComponent().addClassName("frame");
    getBoundComponent().add(comboBox);

    String[] categories = { "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
        "Pet Supplies", "Toys and Games" };

    comboBox.setDropdownType("demo-dropdown-type");
    comboBox.insert(categories)
        .selectIndex(0);
  }
}
