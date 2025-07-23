package com.webforj.samples.views.progressbar;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progress Bar Determinate")
public class ProgressBarDeterminateView extends Composite<Div> {

  public ProgressBarDeterminateView() {
    ProgressBar bar = new ProgressBar();
    bar.setIndeterminate(true)
        .setAnimated(true)
        .setStriped(true)
        .setText("Loading...");

    getBoundComponent().setMaxWidth("320px")
        .setStyle("margin", "0 auto")
        .setStyle("padding", "20px");
    getBoundComponent().add(bar);
  }
}
