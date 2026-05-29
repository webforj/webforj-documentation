package com.webforj.samples.views.progressbar;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progress Bar Themes")
public class ProgressBarThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ProgressBarThemesView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMaxWidth("320px")
        .setStyle("margin", "0 auto")
        .setStyle("padding", "20px");

    for (Theme theme : Theme.values()) {
      ProgressBar bar =
          new ProgressBar()
              .setAnimated(true)
              .setValue(50)
              .setStriped(true)
              .setText(theme.name() + " {{x}}%")
              .setTheme(theme);

      self.add(bar);
    }
  }
}
