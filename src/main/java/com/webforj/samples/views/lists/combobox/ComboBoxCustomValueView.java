package com.webforj.samples.views.lists.combobox;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ComboBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("ComboBox Custom Value")
public class ComboBoxCustomValueView extends Composite<FlexLayout> {

  ComboBox customValue = new ComboBox("Department");
  RadioButton toggle = RadioButton.Switch("Toggle Custom Value");

  public ComboBoxCustomValueView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setMargin("20px 0 0 20px").setSpacing("20px").setWidth(200);
    getBoundComponent().add(customValue, toggle);

    String[] categories = { "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
        "Pet Supplies", "Toys and Games" };

    customValue.insert(categories);
    customValue.setAllowCustomValue(false);

    toggle.onToggle(e -> customValue.setAllowCustomValue(!customValue.isAllowCustomValue()));

  }
}
