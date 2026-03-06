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
  // Categories for the ComboBox
  private static final String[] CATEGORIES = {
      "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
      "Pet Supplies", "Toys and Games"
  };

  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final ComboBox customValue = new ComboBox("Department");
  private final RadioButton toggle = RadioButton.Switch("Toggle Custom Value");

  public ComboBoxCustomValueView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.COLUMN)
        .setMargin("20px 0 0 20px")
        .setSpacing("20px")
        .setWidth(200);

    // Insert categories and disable custom values by default
    customValue.insert(CATEGORIES)
        .setAllowCustomValue(false);

    // Toggle custom value setting when switch is toggled
    toggle.onToggle(e -> customValue.setAllowCustomValue(!customValue.isAllowCustomValue()));

    self.add(customValue, toggle);
  }
}
