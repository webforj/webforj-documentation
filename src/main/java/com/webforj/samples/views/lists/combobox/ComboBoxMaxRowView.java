package com.webforj.samples.views.lists.combobox;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ComboBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("ComboBox Max Row")
public class ComboBoxMaxRowView extends Composite<FlexLayout> {

  ComboBox comboBox = new ComboBox("States");
  NumberField numberField = new NumberField("Number of Rows");
  Button select = new Button("Apply", ButtonTheme.PRIMARY);

  public ComboBoxMaxRowView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setMargin("20px 0 0 20px").setSpacing("20px").setWidth(200);
    getBoundComponent().add(comboBox, numberField, select);

    numberField.setStep(1.0)
        .setMax(50.0)
        .setMin(0.0)
        .setRequired(true)
        .setValue((double) comboBox.getMaxRowCount());

    numberField.onValueChange(e -> {
      select.setEnabled(!numberField.isInvalid());
    });

    select.onClick(e -> {
      comboBox.setMaxRowCount(numberField.getValue().intValue());
    });

    String[] states = { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
        "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
        "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
        "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
        "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas",
        "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };
    comboBox.insert(states);
  }
}
