package com.webforj.samples.views.lists.choicebox;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Choicebox Max Rows")
public class ChoiceboxMaxRowView extends Composite<FlexLayout> {

  ChoiceBox demoBox = new ChoiceBox("States");
  NumberField numberField = new NumberField("Number of Rows");
  Button select = new Button("Apply", ButtonTheme.PRIMARY);

  public ChoiceboxMaxRowView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setMargin("20px 0 0 20px").setSpacing("20px").setWidth(200);
    getBoundComponent().add(demoBox, numberField, select);
    numberField.setInvalidMessage("Number of rows cannot be empty.");
    select.onClick(e -> {
      if (!numberField.getText().isBlank())
        demoBox.setMaxRowCount(Integer.valueOf(numberField.getText()));
      else
        numberField.setInvalid(true);
    });

    String[] states = { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
        "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
        "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
        "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
        "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas",
        "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };

    demoBox.insert(states);
    demoBox.selectIndex(0);
  }
}
