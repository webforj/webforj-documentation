package com.webforj.samples.views.toast;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
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
@StyleSheet("ws://css/toast/toast.css")
public class ToastView extends Composite<Div> {
  private final Toast toast = new Toast("", -1);

  public ToastView() {
    
    toast.addClassName("gray-toast");
    
    FlexLayout toastContent = new FlexLayout()
        .setDirection(FlexDirection.ROW)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)");

    toastContent.add(
        new Spinner(),
        new Paragraph("System update failed. Restoring to the previous state.")
          .setStyle("text-align", "center"),
        new Button("Stop", ButtonTheme.DANGER, e -> toast.close())
    );

    toast.add(toastContent);
    toast.open();
  }
}
