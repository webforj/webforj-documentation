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

  public ClosableAlertView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setSpacing("var(--dwc-space-m)")
          .setAlignment(FlexAlignment.CENTER)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setMargin("var(--dwc-space-l)");

    Alert alert = new Alert("Heads up! This alert can be dismissed.")
        .setTheme(Theme.INFO)
        .setClosable(true)
        .setMaxWidth("400px");

    Button reopenButton = new Button("Show Alert").setTheme(ButtonTheme.PRIMARY);
    reopenButton.setVisible(false);

    alert.onClose((AlertCloseEvent e) -> {
      reopenButton.setVisible(true);
    });

    reopenButton.addClickListener(e -> {
      alert.open();
      reopenButton.setVisible(false);
    });

    layout.add(alert, reopenButton);
  }
}