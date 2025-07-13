package com.webforj.samples.views.toast;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TOAST_VIEW)
@FrameTitle("Toast Basics")
public class ToastView extends Composite<Div> {

  Toast t = new Toast("", -1, Theme.GRAY);

  public ToastView() {
    t.add(
        new Spinner(),
        new Paragraph("System update failed. Restoring to the previous state."),
        new Button("Stop", ButtonTheme.DANGER, e -> t.close()));
    t.open();
  }
}
