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

  public ProgressBarThemesView () {
    FlexLayout layout = FlexLayout.create(getBoundComponent()).vertical().build()
        .setMaxWidth("320px")
        .setStyle("margin", "0 auto")
        .setStyle("padding", "20px");
    getBoundComponent().add(layout);

    for (Theme theme : Theme.values()) {
      ProgressBar bar = new ProgressBar();
      bar.setAnimated(true)
          .setValue(50)
          .setStriped(true)
          .setText(theme.name() + " {{x}}%");
      bar.setTheme(theme);
      layout.add(bar);
    }
  }
}
