package com.webforj.samples.views.progressbar;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progress Bar Themes")
public class ProgressBarThemesView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public ProgressBarThemesView() {
    // Create vertical layout container
    FlexLayout layout = FlexLayout.create(self)
        .vertical()
        .build()
        .setMaxWidth("320px")
        .setStyle("margin", "0 auto")
        .setStyle("padding", "20px");

    // Create progress bars for each theme
    for (Theme theme : Theme.values()) {
      ProgressBar bar = new ProgressBar()
          .setAnimated(true)
          .setValue(50)
          .setStriped(true)
          .setText(theme.name() + " {{x}}%")
          .setTheme(theme);

      layout.add(bar);
    }
  }
}
