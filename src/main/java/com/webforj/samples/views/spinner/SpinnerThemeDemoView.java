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
@FrameTitle("Spinner Themes")
public class SpinnerThemeDemoView extends Composite<FlexLayout> {
  
  Spinner defaultSpinner;
  Spinner primarySpinner;
  Spinner successSpinner;
  Spinner dangerSpinner;
  Spinner warningSpinner;
  Spinner graySpinner;
  Spinner infoSpinner;

  public SpinnerThemeDemoView() {
    getBoundComponent().setDirection(FlexDirection.ROW).setAlignment(FlexAlignment.CENTER)
    .setJustifyContent(FlexJustifyContent.CENTER).setSpacing("var(--dwc-space-m)")
    .setMargin("var(--dwc-space-l)");
    
    defaultSpinner = new Spinner(Theme.DEFAULT, SpinnerExpanse.MEDIUM);
    primarySpinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM);
    successSpinner = new Spinner(Theme.SUCCESS, SpinnerExpanse.MEDIUM);
    dangerSpinner = new Spinner(Theme.DANGER, SpinnerExpanse.MEDIUM);
    warningSpinner = new Spinner(Theme.WARNING, SpinnerExpanse.MEDIUM);
    graySpinner = new Spinner(Theme.GRAY, SpinnerExpanse.MEDIUM);
    infoSpinner = new Spinner(Theme.INFO, SpinnerExpanse.MEDIUM);
    
    getBoundComponent().add(defaultSpinner, primarySpinner, successSpinner, dangerSpinner, 
        warningSpinner, graySpinner, infoSpinner);
  }
}
