package com.webforj.samples.views.lists.choicebox;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/lists/combobox/comboBoxDropDownType.css")
@Route(RouteConfig.CHOICE_BOX_DROPDOWN)
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
