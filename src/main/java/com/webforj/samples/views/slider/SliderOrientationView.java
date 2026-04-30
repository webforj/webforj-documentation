package com.webforj.samples.views.slider;

import static java.util.Map.entry;

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

@Route
@FrameTitle("Slider Orientation")
public class SliderOrientationView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // Vertical orientation slider
  private final Slider volumeSlider = new Slider();

  public SliderOrientationView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)");

    volumeSlider
        .setMin(0)
        .setMax(100)
        .setFilled(true)
        .setTheme(Theme.GRAY)
        .setOrientation(Slider.Orientation.VERTICAL)
        .setTicksVisible(true)
        .setMajorTickSpacing(20)
        .setMinorTickSpacing(10)
        .setLabels(
            Map.ofEntries(
                entry(0, "Mute"),
                entry(20, "20%"),
                entry(40, "40%"),
                entry(60, "60%"),
                entry(80, "80%"),
                entry(100, "Max")))
        .setLabelsVisible(true)
        .setTooltipVisible(true)
        .setTooltipVisibleOnSlideOnly(true);

    IconButton muteButton = new IconButton(TablerIcon.create("volume-off"));
    muteButton
        .setTheme(Theme.DANGER)
        .onClick(
            e -> {
              volumeSlider.setValue(0);
            });

    IconButton maxVolumeButton = new IconButton(TablerIcon.create("volume-2"));
    maxVolumeButton.onClick(
        e -> {
          volumeSlider.setValue(100);
        });

    FlexLayout sliderContainer =
        FlexLayout.create(maxVolumeButton, volumeSlider, muteButton)
            .vertical()
            .justify()
            .center()
            .align()
            .center()
            .build()
            .setSpacing("var(--dwc-space-m)");

    self.add(sliderContainer);
  }
}
