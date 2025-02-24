package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.Map;

import static java.util.Map.entry;

@Route
@FrameTitle("Temperature Selector Demo")
public class SliderTempView extends Composite<FlexLayout> {

  Slider temperatureSlider = new Slider();
  Integer currentTemperature = 72;
  FlexLayout layout = getBoundComponent();

  public SliderTempView() {
    layout.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    temperatureSlider.setMin(60)
        .setMax(90)
        .setValue(currentTemperature)
        .setTicksVisible(true)
        .setMajorTickSpacing(10)
        .setMinorTickSpacing(5)
        .setLabels(Map.ofEntries(
            entry(60, "60°F"),
            entry(70, "70°F"),
            entry(80, "80°F"),
            entry(90, "90°F")
        ))
        .setLabelsVisible(true)
        .setTooltipVisibleOnSlideOnly(true)
        .setWidth("300px");

    IconButton snowflakeButton = new IconButton(TablerIcon.create("snowflake"));
    snowflakeButton.setTheme(Theme.PRIMARY)
        .setStyle("font-size", "1.5rem")
        .onClick(e -> {
          currentTemperature = 60;
          temperatureSlider.setValue(60);
        });

    IconButton sunButton = new IconButton(TablerIcon.create("sun"));
    sunButton.setTheme(Theme.DANGER)
        .setStyle("font-size", "1.5rem")
        .onClick(e -> {
          currentTemperature = 90;
          temperatureSlider.setValue(90);
        });

    FlexLayout sliderContainer = new FlexLayout();
    sliderContainer.setDirection(FlexDirection.ROW)
        .setJustifyContent(FlexJustifyContent.BETWEEN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)");

    sliderContainer.add(snowflakeButton, temperatureSlider, sunButton);

    layout.add(sliderContainer);
  }
}