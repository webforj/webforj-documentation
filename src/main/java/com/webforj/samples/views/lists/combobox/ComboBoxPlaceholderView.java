package com.webforj.samples.views.lists.combobox;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("ComboBox Placeholders")
public class ComboBoxPlaceholderView extends Composite<FlexLayout> {

  ComboBox comboBox = new ComboBox();

  public ComboBoxPlaceholderView() {
    getBoundComponent().setMargin("20px 0 0 20px").setSpacing("20px");

    String[] categories = { "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
        "Pet Supplies", "Toys and Games" };

    comboBox.setPlaceholder("Example Placeholder");
    comboBox.insert(categories);
    getBoundComponent().add(comboBox);
  }
}
