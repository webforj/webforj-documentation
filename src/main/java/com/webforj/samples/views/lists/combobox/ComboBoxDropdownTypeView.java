package com.webforj.samples.views.lists.combobox;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/lists/combobox/comboBoxDropDownType.css")
@Route(RouteConfig.COMBO_BOX_DROPDOWN)
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
