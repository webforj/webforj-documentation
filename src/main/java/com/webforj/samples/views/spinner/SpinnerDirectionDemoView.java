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

  Spinner spinner;
  Button clockwiseButton;
  Button counterClockwiseButton;
  FlexLayout buttonContainer;

  public SpinnerDirectionDemoView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER).setSpacing("var(--dwc-space-m")
        .setMargin("var(--dwc-space-l)");

    spinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM);
    clockwiseButton = new Button("Clockwise", e -> spinner.setClockwise(true)).setWidth("200px");
    counterClockwiseButton = new Button("Counterclockwise", e -> spinner.setClockwise(false))
        .setWidth("200px");
    
    buttonContainer = new FlexLayout(clockwiseButton, counterClockwiseButton);
    buttonContainer.setMargin("var(--dwc-space-s)");

    getBoundComponent().add(spinner, buttonContainer);
  }
}
