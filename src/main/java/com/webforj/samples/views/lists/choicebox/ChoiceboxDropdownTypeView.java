package com.webforj.samples.views.lists.choicebox;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/lists/combobox/comboBoxDropDownType.css")
@Route
@FrameTitle("Choicebox Dropdown Type")
public class ChoiceboxDropdownTypeView extends Composite<Div> {

  ChoiceBox demoBox = new ChoiceBox("Department");

  public ChoiceboxDropdownTypeView() {
    getBoundComponent().addClassName("frame");
    getBoundComponent().add(demoBox);

    String[] categories = { "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
        "Pet Supplies", "Toys and Games" };

    demoBox.setDropdownType("demo-dropdown-type");
    demoBox.insert(categories)
        .selectIndex(0);
  }
}
