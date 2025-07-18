package com.webforj.samples.views.toast;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("toasttheme")
@FrameTitle("Toast Themes")
@InlineStyleSheet("context://css/toast/toastTheme.css")
public class ToastThemeView extends Composite<FlexLayout> {

  Toast updateToast;
  Button laterButton;
  Button updateButton;

  public ToastThemeView() {
    getBoundComponent().setMargin("var(--dwc-space-l)");

    updateToast = new Toast("The application has a new update available", -1, Theme.DEFAULT);
    updateToast.addClassName("custom-theme");
    updateToast.open();
  }

}
