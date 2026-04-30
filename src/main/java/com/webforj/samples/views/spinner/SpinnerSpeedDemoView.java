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
  private final FlexLayout self = getBoundComponent();

  // UI Components
  private final Spinner spinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM);
  private final Button slowButton;
  private final Button mediumButton;
  private final Button fastButton;
  private final Button pauseResumeButton;

  public SpinnerSpeedDemoView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)");

    slowButton = new Button("Slow", e -> setSpinnerSpeed(spinner, 1000));
    mediumButton = new Button("Medium", e -> setSpinnerSpeed(spinner, 500));
    fastButton = new Button("Fast", e -> setSpinnerSpeed(spinner, 200));
    pauseResumeButton = new Button("Pause", ButtonTheme.PRIMARY, e -> spinner.setPaused(true));

    FlexLayout buttons =
        new FlexLayout(slowButton, mediumButton, fastButton, pauseResumeButton)
            .setMargin("var(--dwc-space-s)");

    self.add(spinner, buttons);
  }

  /** Sets the spinner speed and resumes animation. */
  private void setSpinnerSpeed(Spinner spinner, int speed) {
    spinner.setSpeed(speed);
    spinner.setPaused(false);
  }
}
