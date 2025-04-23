package com.webforj.samples.views.slider;

import java.util.Map;

import org.checkerframework.checker.units.qual.t;

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

  Slider slider = new Slider(50, 0, 100);

  public SliderLabelsView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("5% auto")
        .setAlignment(FlexAlignment.CENTER);

    slider
        .setFilled(true)
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

          if(value > 0 && value < 30) {
            slider.setTheme(Theme.PRIMARY);
          }

          if(value >= 30 && value < 50) {
            slider.setTheme(Theme.SUCCESS);
          }

          if(value >= 50 && value < 80) {
            slider.setTheme(Theme.WARNING);
          }

          if(value >= 75 && value <= 100) {
            slider.setTheme(Theme.DANGER);
          }
        });

    layout.add(slider);
  }
}
