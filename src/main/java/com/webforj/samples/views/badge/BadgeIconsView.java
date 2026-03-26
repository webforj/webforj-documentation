package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Badge - Icons")
public class BadgeIconsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BadgeIconsView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px")
      .add(new H3("Icon + Text (label prop)"),
        createIconTextRow(),
        new H3("Icon Only"),
        createIconOnlyRow(),
        new H3("All Themes with Icon + Text"),
        createAllThemesRow());
  }

  private FlexLayout createIconTextRow() {
    return FlexLayout.create(
      new Badge("Done", FeatherIcon.CHECK_CIRCLE.create()).setTheme(BadgeTheme.SUCCESS),
      new Badge("Error", FeatherIcon.X_CIRCLE.create()).setTheme(BadgeTheme.DANGER),
      new Badge("Info", FeatherIcon.INFO.create()).setTheme(BadgeTheme.PRIMARY),
      new Badge("Warning", FeatherIcon.ALERT_TRIANGLE.create()).setTheme(BadgeTheme.WARNING)
    ).horizontal().wrap().build().setSpacing("var(--dwc-space-s)").setAlignment(FlexAlignment.CENTER);
  }

  private FlexLayout createIconOnlyRow() {
    return FlexLayout.create(
      new Badge(FeatherIcon.CHECK.create()).setTheme(BadgeTheme.SUCCESS),
      new Badge(FeatherIcon.X.create()).setTheme(BadgeTheme.DANGER),
      new Badge(FeatherIcon.BELL.create()).setTheme(BadgeTheme.PRIMARY),
      new Badge(FeatherIcon.STAR.create()).setTheme(BadgeTheme.GRAY)
    ).horizontal().build().setSpacing("var(--dwc-space-s)").setAlignment(FlexAlignment.CENTER);
  }

  private FlexLayout createAllThemesRow() {
    return FlexLayout.create(
      new Badge("New", FeatherIcon.STAR.create()).setTheme(BadgeTheme.PRIMARY),
      new Badge("Live", FeatherIcon.RADIO.create()).setTheme(BadgeTheme.SUCCESS),
      new Badge("Alert", FeatherIcon.ALERT_CIRCLE.create()).setTheme(BadgeTheme.DANGER),
      new Badge("Draft", FeatherIcon.EDIT.create()).setTheme(BadgeTheme.GRAY),
      new Badge("Beta", FeatherIcon.ZAP.create()).setTheme(BadgeTheme.WARNING)
    ).horizontal().wrap().build().setSpacing("var(--dwc-space-s)").setAlignment(FlexAlignment.CENTER);
  }
}
