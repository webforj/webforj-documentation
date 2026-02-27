package com.webforj.samples.views.toast;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Toast Basics")
public class ToastView extends Composite<Div> {
  private final Toast toast = new Toast("", -1, Theme.GRAY);

  public ToastView() {
    FlexLayout toastContent = new FlexLayout()
        .setDirection(FlexDirection.ROW)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)");

    toastContent.add(
        new Spinner(),
        new Paragraph("System update failed. Restoring to the previous state."),
        new Button("Stop", ButtonTheme.DANGER, e -> toast.close())
    );

    toast.add(toastContent);
    toast.open();
  }
}
