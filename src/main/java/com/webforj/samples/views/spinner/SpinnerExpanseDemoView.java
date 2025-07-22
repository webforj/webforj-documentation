package com.webforj.samples.views.spinner;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Spinner Expanses")
public class SpinnerExpanseDemoView extends Composite<FlexLayout> {
  
  Spinner smallSpinner;
  Spinner mediumSpinner;
  Spinner largeSpinner;

  public SpinnerExpanseDemoView() {
    smallSpinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.SMALL);
    mediumSpinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM);
    largeSpinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.LARGE);
    
    getBoundComponent().setDirection(FlexDirection.ROW).setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER).setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)");
    getBoundComponent().add(smallSpinner, mediumSpinner, largeSpinner);
  }
}
