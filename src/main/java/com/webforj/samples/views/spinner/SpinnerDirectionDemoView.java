package com.webforj.samples.views.spinner;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Spinner Directions")
public class SpinnerDirectionDemoView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final Spinner spinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM);
  private final Button clockwiseButton;
  private final Button counterClockwiseButton;
  private final FlexLayout buttonContainer;

  public SpinnerDirectionDemoView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)");

    // Create direction control buttons
    clockwiseButton = new Button("Clockwise", e -> spinner.setClockwise(true))
        .setWidth("200px");

    counterClockwiseButton = new Button("Counterclockwise", e -> spinner.setClockwise(false))
        .setWidth("200px");

    // Create button container
    buttonContainer = new FlexLayout(clockwiseButton, counterClockwiseButton)
            .setMargin("var(--dwc-space-s)");

    self.add(spinner, buttonContainer);
  }
}
