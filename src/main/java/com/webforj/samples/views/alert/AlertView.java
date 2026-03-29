package com.webforj.samples.views.alert;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Form Confirmation Alert")
public class AlertView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Button viewButton = new Button("View", ButtonTheme.PRIMARY);
  private final Alert alert = new Alert();

  public AlertView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setAlignment(FlexAlignment.CENTER)
      .setJustifyContent(FlexJustifyContent.CENTER)
      .setMargin("var(--dwc-space-l)")
      .add(alert);

    alert.setTheme(Theme.PRIMARY)
      .setMaxWidth("500px")
      .add(new Paragraph("The requested information is ready to be viewed."), viewButton);
  }
}
