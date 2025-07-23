package com.webforj.samples.views.radiobutton;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButton.Activation;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Radio Button Activation")
public class RadioButtonActivationView extends Composite<FlexLayout> {

  public RadioButtonActivationView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setSpacing("1em").setMargin("20px");

    RadioButton autoActivateOne = new RadioButton("Auto Activated");
    autoActivateOne.setActivation(Activation.AUTO)
        .focus();

    RadioButton autoActivateTwo = new RadioButton("Auto Activated");
    autoActivateTwo.setActivation(Activation.AUTO);

    RadioButton manualActivateOne = new RadioButton("Manually Activated");
    RadioButton manualActivateTwo = new RadioButton("Manually Activated");

    getBoundComponent().add(autoActivateOne, autoActivateTwo, manualActivateOne, manualActivateTwo);
  }
}
