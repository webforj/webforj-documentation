package com.webforj.samples.views.spinner;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Spinner Speeds")
public class SpinnerSpeedDemoView extends Composite<FlexLayout> {
  
  Spinner spinner;
  Button slowButton;
  Button mediumButton;
  Button fastButton;
  
  Button pauseResumeButton;

  public SpinnerSpeedDemoView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setAlignment(FlexAlignment.CENTER)
    .setJustifyContent(FlexJustifyContent.CENTER).setSpacing("var(--dwc-space-m)")
    .setMargin("var(--dwc-space-l)");
    
    spinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM);
    slowButton = new Button("Slow", e -> setSpinnerSpeed(spinner, 1000));
    mediumButton = new Button("Medium", e -> setSpinnerSpeed(spinner, 500));
    fastButton = new Button("Fast", e -> setSpinnerSpeed(spinner, 200));
    pauseResumeButton = new Button("Pause", e ->  spinner.setPaused(true))
        .setTheme(ButtonTheme.PRIMARY);
    
    FlexLayout buttons = new FlexLayout(slowButton, mediumButton, fastButton, pauseResumeButton);
    buttons.setMargin("var(--dwc-space-s)");

    getBoundComponent().add(spinner, buttons);
  }

  private void setSpinnerSpeed(Spinner spinner, int speed) {
    spinner.setSpeed(speed);
    spinner.setPaused(false);
  }

}