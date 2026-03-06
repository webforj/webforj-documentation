package com.webforj.samples.views.slider;

import java.util.Map;

import com.webforj.component.Composite;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import static java.util.Map.entry;

@Route
@FrameTitle("Brightness Adjustment Demo")
public class SliderBrightnessView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // Brightness slider component
  private final Slider brightnessSlider = new Slider();

  // Current brightness state
  private Integer currentBrightness = 50;

  public SliderBrightnessView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .setAlignContent(FlexContentAlignment.CENTER);

    // Configure brightness slider
    brightnessSlider.setMin(0)
        .setMax(100)
        .setValue(currentBrightness)
        .setMajorTickSpacing(25)
        .setMinorTickSpacing(5)
        .setTicksVisible(true)
        .setLabels(Map.ofEntries(
            entry(0, "Dim"),
            entry(50, "Normal"),
            entry(100, "Bright")
        ))
        .setLabelsVisible(true)
        .setTooltipVisibleOnSlideOnly(true)
        .setWidth("300px");

    // Create low brightness button
    IconButton lowBrightnessButton = new IconButton(TablerIcon.create("sun-low"));
    lowBrightnessButton.onClick(e -> {
      currentBrightness = 0;
      brightnessSlider.setValue(0);
    });

    // Create high brightness button
    IconButton highBrightnessButton = new IconButton(TablerIcon.create("sun-high"));
    highBrightnessButton.onClick(e -> {
      currentBrightness = 100;
      brightnessSlider.setValue(100);
    });

    // Create horizontal slider container
    FlexLayout sliderContainer = FlexLayout.create(lowBrightnessButton, brightnessSlider, highBrightnessButton)
            .horizontal()
            .justify().center()
            .align().center()
            .build()
            .setSpacing("var(--dwc-space-m)");

    self.add(sliderContainer);
  }
}