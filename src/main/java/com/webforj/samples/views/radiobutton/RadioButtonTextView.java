package com.webforj.samples.views.radiobutton;

import com.webforj.concern.HasTextPosition.Position;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Radio Button Text")
public class RadioButtonTextView extends Composite<FlexLayout> {

  public RadioButtonTextView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setSpacing("1em").setMargin("20px");
    RadioButton buttonRight = new RadioButton("Right aligned (default)");
    RadioButton buttonLeft = new RadioButton("Left aligned")
        .setTextPosition(Position.LEFT);
    getBoundComponent().add(buttonRight, buttonLeft, new CheckBox("CheckBox"));
  }
}
