package com.webforj.samples.views.alert;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.alert.event.AlertCloseEvent;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Closable Alert")
public class ClosableAlertView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Alert alert = new Alert("Heads up! This alert can be dismissed.");
  private final Button reopenButton = new Button("Show Alert");

  public ClosableAlertView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-l)")
        .add(alert, reopenButton);

    alert.setTheme(Theme.INFO)
        .setClosable(true)
        .setMaxWidth("400px")
        .onClose(e -> reopenButton.setVisible(true));

    reopenButton.setTheme(ButtonTheme.PRIMARY)
        .setVisible(false)
        .onClick(e -> {
          alert.open();
          reopenButton.setVisible(false);
        });
  }
}
