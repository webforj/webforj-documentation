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
  // US States for the ChoiceBox
  private static final String[] STATES = {
      "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
      "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
      "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
      "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
      "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas",
      "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
  };

  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final ChoiceBox choiceBox = new ChoiceBox("States");
  private final NumberField numberField = new NumberField("Number of Rows");
  private final Button select = new Button("Apply", ButtonTheme.PRIMARY);

  public ChoiceboxMaxRowView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.COLUMN)
        .setMargin("20px 0 0 20px")
        .setSpacing("20px")
        .setWidth(200);

    // Configure number field with validation constraints
    numberField.setStep(1.0)
        .setMax(50.0)
        .setMin(0.0)
        .setRequired(true)
        .setValue((double) choiceBox.getMaxRowCount());

    // Enable/disable apply button based on field validity
    numberField.onValueChange(e -> select.setEnabled(!numberField.isInvalid()));

    // Apply max row count when button is clicked
    select.onClick(e -> {
      if (!numberField.isInvalid()) {
        choiceBox.setMaxRowCount(numberField.getValue().intValue());
      } else {
        numberField.focus();
      }
    });

    // Insert states and select first by default
    choiceBox.insert(STATES)
        .selectIndex(0);

    self.add(choiceBox, numberField, select);
  }
}
