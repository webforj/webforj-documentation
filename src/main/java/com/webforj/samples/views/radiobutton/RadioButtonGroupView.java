package com.webforj.samples.views.radiobutton;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Radio Button Group")
public class RadioButtonGroupView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public RadioButtonGroupView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("1em")
        .setMargin("20px");

    // Create radio buttons for the group
    RadioButton sDisagree = new RadioButton("Strongly Disagree");
    RadioButton disagree = new RadioButton("Disagree");
    RadioButton neutral = new RadioButton("Neutral");
    RadioButton agree = new RadioButton("Agree");
    RadioButton sAgree = new RadioButton("Strongly Agree");

    // Create radio button group
    RadioButtonGroup group = new RadioButtonGroup(sDisagree, disagree, neutral, agree, sAgree);

    self.add(group);
  }
}
