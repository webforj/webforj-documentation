package com.webforj.samples.views.radiobutton;

import com.webforj.component.Composite;
import com.webforj.concern.HasTextPosition.Position;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Radio Button Text")
public class RadioButtonTextView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public RadioButtonTextView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("1em")
        .setMargin("20px");

    // Radio button with text on right (default)
    RadioButton buttonRight = new RadioButton("Right aligned (default)");

    // Radio button with text on left
    RadioButton buttonLeft = new RadioButton("Left aligned")
        .setTextPosition(Position.LEFT);

    // CheckBox for comparison
    CheckBox checkBox = new CheckBox("CheckBox");

    self.add(buttonRight, buttonLeft, checkBox);
  }
}
