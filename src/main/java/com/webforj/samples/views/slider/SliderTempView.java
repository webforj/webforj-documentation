package com.webforj.samples.views.slider;

import java.util.Map;

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

import static java.util.Map.entry;

@Route
@FrameTitle("Temperature Selector Demo")
public class SliderTempView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // Temperature slider component
  private final Slider temperatureSlider = new Slider();

  public SliderTempView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    // Configure temperature slider
    temperatureSlider.setMin(60)
        .setMax(90)
        .setValue(72)
        .setTicksVisible(true)
        .setMajorTickSpacing(10)
        .setMinorTickSpacing(5)
        .setLabels(Map.ofEntries(
            entry(60, "60&deg;F"),
            entry(70, "70&deg;F"),
            entry(80, "80&deg;F"),
            entry(90, "90&deg;F")
        ))
        .setLabelsVisible(true)
        .setTooltipVisibleOnSlideOnly(true)
        .setWidth("300px");

    // Create cold (snowflake) button
    IconButton snowflakeButton = new IconButton(TablerIcon.create("snowflake"));
    snowflakeButton.setTheme(Theme.PRIMARY)
        .setStyle("font-size", "1.5rem")
        .onClick(e -> {
          temperatureSlider.setValue(60);
        });

    // Create hot (sun) button
    IconButton sunButton = new IconButton(TablerIcon.create("sun"));
    sunButton.setTheme(Theme.DANGER)
        .setStyle("font-size", "1.5rem")
        .onClick(e -> {
          temperatureSlider.setValue(90);
        });

    // Create horizontal slider container
    FlexLayout sliderContainer = FlexLayout.create(snowflakeButton, temperatureSlider, sunButton)
            .horizontal()
            .justify().between()
            .align().center()
            .build()
            .setSpacing("var(--dwc-space-m)");

    self.add(sliderContainer);
  }
}
