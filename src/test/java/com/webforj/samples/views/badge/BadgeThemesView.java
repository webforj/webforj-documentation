package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/themes")
@FrameTitle("Badge - Themes")
public class BadgeThemesView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public BadgeThemesView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Default"));
    self.add(createRow(
        new Badge("Default", BadgeTheme.DEFAULT),
        new Badge("Primary", BadgeTheme.PRIMARY),
        new Badge("Success", BadgeTheme.SUCCESS),
        new Badge("Warning", BadgeTheme.WARNING),
        new Badge("Danger", BadgeTheme.DANGER),
        new Badge("Info", BadgeTheme.INFO),
        new Badge("Gray", BadgeTheme.GRAY)));

    self.add(new H3("Outlined"));
    self.add(createRow(
        new Badge("Default", BadgeTheme.OUTLINED_DEFAULT),
        new Badge("Primary", BadgeTheme.OUTLINED_PRIMARY),
        new Badge("Success", BadgeTheme.OUTLINED_SUCCESS),
        new Badge("Warning", BadgeTheme.OUTLINED_WARNING),
        new Badge("Danger", BadgeTheme.OUTLINED_DANGER),
        new Badge("Info", BadgeTheme.OUTLINED_INFO),
        new Badge("Gray", BadgeTheme.OUTLINED_GRAY)));

  }

  private FlexLayout createRow(Badge... badges) {
    FlexLayout row = FlexLayout.create().horizontal().build();
    row.setSpacing("var(--dwc-space-s)");
    row.setStyle("flex-wrap", "wrap");
    row.setStyle("align-items", "center");
    row.add(badges);
    return row;
  }
}
