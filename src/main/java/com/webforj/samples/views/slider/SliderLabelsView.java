package com.webforj.samples.views.slider;

import java.util.Map;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import static java.util.Map.entry;

@Route
@FrameTitle("Slider Tick and Non-Tick Demo")
public class SliderLabelsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // Slider with temperature labels
  private final Slider slider = new Slider(50, 0, 100);

  public SliderLabelsView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("5% auto")
        .setAlignment(FlexAlignment.CENTER);

    // Configure slider with labels, ticks, and dynamic theming
    slider.setFilled(true)
        .setTicksVisible(true)
        .setMajorTickSpacing(10)
        .setMinorTickSpacing(2)
        .setLabelsVisible(true)
        .setAllowMajorLabelsOverlap(true)
        .setTooltipVisible(true)
        .setTheme(Theme.SUCCESS)
        .setLabels(Map.ofEntries(
            entry(0, "Cold"),
            entry(30, "Cool"),
            entry(50, "Moderate"),
            entry(80, "Warm"),
            entry(100, "Hot")))
        .setSnapToTicks(true)
        .setWidth("500px")
        .onValueChange(e -> {
          Integer value = e.getValue();

          // Change theme based on temperature value
          if (value > 0 && value < 30) {
            slider.setTheme(Theme.PRIMARY);
          } else if (value >= 30 && value < 50) {
            slider.setTheme(Theme.SUCCESS);
          } else if (value >= 50 && value < 80) {
            slider.setTheme(Theme.WARNING);
          } else if (value >= 80 && value <= 100) {
            slider.setTheme(Theme.DANGER);
          }
        });

    self.add(slider);
  }
}
