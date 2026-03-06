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
@FrameTitle("Volume Control Demo")
public class SliderView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // Volume slider component
  private final Slider volumeSlider = new Slider();

  // Current volume state
  private Integer currentVolume = 50;

  public SliderView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)");

    // Configure volume slider with labels and ticks
    volumeSlider.setMin(0)
        .setMax(100)
        .setValue(currentVolume)
        .setTicksVisible(true)
        .setMajorTickSpacing(20)
        .setMinorTickSpacing(10)
        .setLabels(Map.ofEntries(
            entry(0, "Mute"),
            entry(20, "20%"),
            entry(40, "40%"),
            entry(60, "60%"),
            entry(80, "80%"),
            entry(100, "Max")))
        .setLabelsVisible(true)
        .setTooltipVisible(true)
        .setTooltipVisibleOnSlideOnly(true)
        .setWidth("300px");

    // Create mute button
    IconButton muteButton = new IconButton(TablerIcon.create("volume-off"));
    muteButton.setTheme(Theme.DANGER)
        .onClick(e -> {
          currentVolume = 0;
          volumeSlider.setValue(0);
        });

    // Create max volume button
    IconButton maxVolumeButton = new IconButton(TablerIcon.create("volume-2"));
    maxVolumeButton.onClick(e -> {
      currentVolume = 100;
      volumeSlider.setValue(100);
    });

    // Create horizontal slider container
    FlexLayout sliderContainer = FlexLayout.create(muteButton, volumeSlider, maxVolumeButton)
            .horizontal()
            .justify().center()
            .align().center()
            .build()
            .setSpacing("var(--dwc-space-m)");

    self.add(sliderContainer);
  }
}
