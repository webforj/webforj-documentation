package com.webforj.samples.views.toast;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Toast Themes")
@StyleSheet("ws://css/toast/toastTheme.css")
public class ToastThemeView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();

  public ToastThemeView() {
    self.setMargin("var(--dwc-space-l)");

    new Toast("The application has a new update available", -1, Theme.DEFAULT)
        .addClassName("custom-theme")
        .open();
  }
}
