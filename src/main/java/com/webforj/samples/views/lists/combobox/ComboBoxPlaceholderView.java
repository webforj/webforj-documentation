package com.webforj.samples.views.lists.combobox;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("ComboBox Placeholders")
public class ComboBoxPlaceholderView extends Composite<FlexLayout> {
  // Categories for the ComboBox
  private static final String[] CATEGORIES = {
      "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
      "Pet Supplies", "Toys and Games"
  };

  private final FlexLayout self = getBoundComponent();
  // ComboBox component without label
  private final ComboBox comboBox = new ComboBox();

  public ComboBoxPlaceholderView() {
    // Configure layout with fluent API
    self.setMargin("20px 0 0 20px")
        .setSpacing("20px");

    // Set placeholder text and insert categories
    comboBox.setPlaceholder("Example Placeholder")
        .insert(CATEGORIES);

    self.add(comboBox);
  }
}
