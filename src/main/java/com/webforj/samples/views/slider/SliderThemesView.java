package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route
@FrameTitle("Slider Themes")
public class SliderThemesView extends Composite<FlexLayout> {
  // Available themes for sliders
  private static final List<Theme> THEMES =
      List.of(Theme.DEFAULT, Theme.DANGER, Theme.GRAY, Theme.INFO, Theme.SUCCESS, Theme.WARNING);

  private final FlexLayout self = getBoundComponent();

  public SliderThemesView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-l)");

    THEMES.stream()
        .map(
            theme ->
                new Slider()
                    .setMax(100)
                    .setMin(0)
                    .setValue(50)
                    .setMajorTickSpacing(10)
                    .setMinorTickSpacing(2)
                    .setTicksVisible(true)
                    .setLabelsVisible(true)
                    .setFilled(true)
                    .setTheme(theme)
                    .setWidth("500px"))
        .forEach(self::add);
  }
}
