package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.Arrays;
import java.util.function.Predicate;

@Route
@FrameTitle("Badge - Themes")
public class BadgeThemesView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public BadgeThemesView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px");

    FlexLayout solidRow = createBadgeRow();
    FlexLayout outlineRow = createBadgeRow();

    for (BadgeTheme theme : BadgeTheme.values()) {
      String themeName = theme.name();
      if (themeName.startsWith("OUTLINED")) {
        String text = themeName.split("_")[1];
        String name = text.transform(s ->
          s.charAt(0) + s.substring(1).toLowerCase());
        outlineRow.add(new Badge(name, theme));
      } else {
        String name = themeName.transform(s ->
          s.charAt(0) + s.substring(1).toLowerCase());
        solidRow.add(new Badge(name, theme));
      }
    }

    self.add(new H3("Default"), solidRow, new H3("Outlined"), outlineRow);
  }

  private FlexLayout createBadgeRow() {
    return FlexLayout.create()
      .horizontal()
      .wrap()
      .build()
      .setSpacing("var(--dwc-space-s)")
      .setAlignment(FlexAlignment.CENTER);
  }
}
