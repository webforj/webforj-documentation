package com.webforj.samples.views.lists.combobox;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@BundleEntry("css/lists/combobox/comboBoxDropDownType.css")
@Route
@FrameTitle("ComboBox Dropdown Type")
public class ComboBoxDropdownTypeView extends Composite<FlexLayout> {
  // Categories for the ComboBox
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
  // ComboBox component with custom dropdown styling
  private final ComboBox comboBox = new ComboBox("Department");

  public ComboBoxDropdownTypeView() {
    self.setWidth("200px").setMargin("20px").add(comboBox);

    comboBox.setDropdownType("demo-dropdown-type").insert(CATEGORIES).selectIndex(0);
  }
}
