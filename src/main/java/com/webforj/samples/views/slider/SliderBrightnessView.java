package com.webforj.samples.views.slider;

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

import java.util.Map;

import static java.util.Map.entry;

import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.SLIDER_BRIGHTNESS)
@FrameTitle("Brightness Adjustment Demo")
public class SliderBrightnessView extends Composite<FlexLayout> {

  Slider brightnessSlider = new Slider();
  Integer currentBrightness = 50;

  public SliderBrightnessView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .setAlignContent(FlexContentAlignment.CENTER);

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

    IconButton lowBrightnessButton = new IconButton(TablerIcon.create("sun-low"));
    lowBrightnessButton.onClick(e -> {
      currentBrightness = 0;
      brightnessSlider.setValue(0);
    });

    IconButton highBrightnessButton = new IconButton(TablerIcon.create("sun-high"));
    highBrightnessButton.onClick(e -> {
      currentBrightness = 100;
      brightnessSlider.setValue(100);
    });

    FlexLayout sliderContainer = new FlexLayout();
    sliderContainer.setDirection(FlexDirection.ROW)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)");
    sliderContainer.add(lowBrightnessButton, brightnessSlider, highBrightnessButton);

    layout.add(sliderContainer);
  }
}