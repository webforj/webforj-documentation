package com.webforj.samples.views.slider;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.SLIDER_THEMES)
@FrameTitle("Slider Themes")
public class SliderThemesView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();

  public SliderThemesView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-l)");

    Theme[] themes = { Theme.DEFAULT, Theme.DANGER, Theme.GRAY, Theme.INFO, Theme.SUCCESS, Theme.WARNING };

    for (int i = 0; i < themes.length; i++) {
      Slider slider = new Slider().setMax(100)
          .setMin(0)
          .setValue(50)
          .setMajorTickSpacing(10)
          .setMinorTickSpacing(2)
          .setTicksVisible(true)
          .setLabelsVisible(true)
          .setFilled(true)
          .setTheme(themes[i])
          .setWidth("500px");

      self.add(slider);
    }
  }
}
