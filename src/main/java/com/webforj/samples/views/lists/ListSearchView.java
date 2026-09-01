package com.webforj.samples.views.lists;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("List Search")
public class ListSearchView extends Composite<FlexLayout> {

  private static final String[] COUNTRIES = {
    "Argentina",
    "Australia",
    "Austria",
    "Belgium",
    "Brazil",
    "Canada",
    "Chile",
    "Denmark",
    "Finland",
    "France",
    "Germany",
    "Ireland",
    "Italy",
    "Italy",
    "Japan",
    "Mexico",
    "Netherlands",
    "New Zealand",
    "Norway",
    "Portugal",
    "Spain",
    "Sweden",
    "Switzerland",
    "United States"
  };

  private final FlexLayout self = getBoundComponent();

  private final ComboBox comboBox = new ComboBox("Country of residence");

  public ListSearchView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setWidth("100%")
        .setMargin("20px 0 0 20px")
        .setSpacing("20px");

    comboBox.setWidth(280).setMaxRowCount(8).insert(COUNTRIES).selectIndex(0);

    self.add(comboBox);

    whenAttached().thenAccept(c -> comboBox.open());

    comboBox
        .getSearch()
        .setFieldVisible(true)
        .setPlaceholder("Search countries")
        .setEmptyMessage("No countries match");
  }
}
