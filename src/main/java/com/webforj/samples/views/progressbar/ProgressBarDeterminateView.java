package com.webforj.samples.views.progressbar;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progress Bar Determinate")
public class ProgressBarDeterminateView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public ProgressBarDeterminateView() {
    // Create indeterminate progress bar with animation
    ProgressBar bar = new ProgressBar()
        .setIndeterminate(true)
        .setAnimated(true)
        .setStriped(true)
        .setText("Loading...");

    // Configure container
    self.setMaxWidth("320px")
        .setStyle("margin", "0 auto")
        .setStyle("padding", "20px")
        .add(bar);
  }
}
