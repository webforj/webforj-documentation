package com.webforj.samples.views.alert;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Alert Expanses")
public class AlertExpansesView extends Composite<FlexLayout> {

  public AlertExpansesView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-xl) auto")
          .setWidth("100%");

    for (int i = Expanse.values().length - 1; i >= 0; i--) {
      Icon icon = TablerIcon.create("alert-square-rounded");
      Paragraph text = new Paragraph("This alert uses the " + Expanse.values()[i].name()
          + " expanse.");

      Alert alert = new Alert()
            .addToContent(FlexLayout.create(icon, text).horizontal().align().center().build())
            .setExpanse(Expanse.values()[i])
            .setClosable(false)
            .setTheme(Theme.SUCCESS)
            .setWidth("380px");
      layout.add(alert);

    }
  }
}
