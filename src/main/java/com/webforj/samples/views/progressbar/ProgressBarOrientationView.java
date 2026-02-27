package com.webforj.samples.views.progressbar;

import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.progressbar.ProgressBar.Orientation;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progress Bar Orientation")
public class ProgressBarOrientationView extends Composite<Div> {
  private final Div self = getBoundComponent();
  // UI Components
  private final Button start = new Button("Start");
  private final Button pause = new Button("Pause");
  private final Button reset = new Button("Reset");

  private final Icon startIcon = TablerIcon.create("player-play");
  private final Icon pauseIcon = TablerIcon.create("player-pause");
  private final Icon resetIcon = TablerIcon.create("refresh");

  private final ProgressBar bar = new ProgressBar(15, "Reactor Heating Up: {{x}}%");
  private final FlexLayout buttonContainer = FlexLayout.create(start, pause, reset).vertical().build();
  private final FlexLayout layout = FlexLayout.create(buttonContainer, bar)
      .horizontal()
      .build()
      .setStyle("max-width", "320px")
      .setStyle("margin", "0 auto")
      .setStyle("padding", "20px");

  public ProgressBarOrientationView() {
    // Configure button icons
    start.setPrefixComponent(startIcon);
    pause.setPrefixComponent(pauseIcon);
    reset.setPrefixComponent(resetIcon);

    // Create interval that updates the progress bar
    Interval interval = new Interval(0.1f, event -> {
      Integer progress = bar.getValue() + 1;
      bar.setValue(progress);

      if (progress >= bar.getMax()) {
        event.getInterval().stop();
        start.setEnabled(false);
        pause.setEnabled(false);
        bar.setAnimated(false);
      }
    });

    // Configure progress bar with vertical orientation
    bar.setStyle("--dwc-progressbar-width", "160px")
        .setStyle("--dwc-progressbar-height", "125px")
        .setTheme(Theme.SUCCESS)
        .setValue(25)
        .setOrientation(Orientation.VERTICAL)
        .setStriped(true)
        .setAnimated(true);

    // Change theme based on progress value
    bar.onValueChange(e -> {
      int value = e.getValue();
      if (value >= 75) {
        bar.setTheme(Theme.DANGER);
      } else if (value >= 25) {
        bar.setTheme(Theme.WARNING);
      } else {
        bar.setTheme(Theme.SUCCESS);
      }
    });

    // Start button click handler
    start.onClick(e -> {
      bar.setAnimated(true);
      start.setEnabled(false);
      pause.setEnabled(true);
      interval.start();
    });

    // Pause button - initially disabled
    pause.setEnabled(false);
    pause.onClick(e -> {
      bar.setAnimated(false);
      start.setEnabled(true);
      pause.setEnabled(false);
      interval.stop();
    });

    // Reset button click handler
    reset.onClick(e -> {
      bar.setValue(0);
      bar.setAnimated(true);
      start.setEnabled(false);
      pause.setEnabled(true);
      interval.restart();
    });

    self.add(layout);
  }
}
