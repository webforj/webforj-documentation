package com.webforj.samples.views.spinner;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Spinner Themes")
public class SpinnerThemeDemoView extends Composite<FlexLayout> {
  // Self reference enabling fluent method chaining
  private final FlexLayout self = getBoundComponent();

  // Available themes
  private static final List<Theme> THEMES = List.of(
      Theme.DEFAULT, Theme.PRIMARY, Theme.SUCCESS, Theme.DANGER,
      Theme.WARNING, Theme.GRAY, Theme.INFO
  );

  public SpinnerThemeDemoView() {
    // Configure layout
    self.setDirection(FlexDirection.ROW)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)");

    // Create spinners with different themes
    THEMES.forEach(theme -> {
      Spinner spinner = new Spinner(theme, SpinnerExpanse.MEDIUM);
      self.add(spinner);
    });
  }
}
