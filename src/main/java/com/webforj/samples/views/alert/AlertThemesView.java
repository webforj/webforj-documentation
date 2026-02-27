package com.webforj.samples.views.alert;

import com.webforj.component.Composite;
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
@FrameTitle("Alert Themes")
public class AlertThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public AlertThemesView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-xl) auto")
        .setWidth("100%");

    for (Theme theme : Theme.values()) {
      Icon icon = TablerIcon.create("alert-square-rounded");
      // Use text block for cleaner string formatting
      Paragraph text = new Paragraph("This is an alert with the %s theme!".formatted(theme));

      Alert alert = new Alert()
          .addToContent(FlexLayout.create(icon, text)
              .horizontal()
              .align().center()
              .build())
          .setTheme(theme)
          .setClosable(false)
          .setWidth("325px");

      self.add(alert);
    }
  }
}
