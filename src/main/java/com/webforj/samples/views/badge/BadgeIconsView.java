package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Badge - Icons")
public class BadgeIconsView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public BadgeIconsView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Icon + Text (label prop)"));

    FlexLayout iconTextRow = FlexLayout.create().horizontal().build();
    iconTextRow.setSpacing("var(--dwc-space-s)");
    iconTextRow.setStyle("flex-wrap", "wrap");
    iconTextRow.setStyle("align-items", "center");

    iconTextRow.add(
        new Badge("Done", FeatherIcon.CHECK_CIRCLE.create()).setTheme(BadgeTheme.SUCCESS),
        new Badge("Error", FeatherIcon.X_CIRCLE.create()).setTheme(BadgeTheme.DANGER),
        new Badge("Info", FeatherIcon.INFO.create()).setTheme(BadgeTheme.PRIMARY),
        new Badge("Warning", FeatherIcon.ALERT_TRIANGLE.create()).setTheme(BadgeTheme.WARNING));

    self.add(iconTextRow);

    self.add(new H3("Icon Only"));

    FlexLayout iconRow = FlexLayout.create().horizontal().build();
    iconRow.setSpacing("var(--dwc-space-s)");
    iconRow.setStyle("align-items", "center");

    iconRow.add(
        new Badge(FeatherIcon.CHECK.create()).setTheme(BadgeTheme.SUCCESS),
        new Badge(FeatherIcon.X.create()).setTheme(BadgeTheme.DANGER),
        new Badge(FeatherIcon.BELL.create()).setTheme(BadgeTheme.PRIMARY),
        new Badge(FeatherIcon.STAR.create()).setTheme(BadgeTheme.GRAY));

    self.add(iconRow);

    self.add(new H3("All Themes with Icon + Text"));

    FlexLayout allThemesRow = FlexLayout.create().horizontal().build();
    allThemesRow.setSpacing("var(--dwc-space-s)");
    allThemesRow.setStyle("flex-wrap", "wrap");
    allThemesRow.setStyle("align-items", "center");

    allThemesRow.add(
        new Badge("New", FeatherIcon.STAR.create()).setTheme(BadgeTheme.PRIMARY),
        new Badge("Live", FeatherIcon.RADIO.create()).setTheme(BadgeTheme.SUCCESS),
        new Badge("Alert", FeatherIcon.ALERT_CIRCLE.create()).setTheme(BadgeTheme.DANGER),
        new Badge("Draft", FeatherIcon.EDIT.create()).setTheme(BadgeTheme.GRAY),
        new Badge("Beta", FeatherIcon.ZAP.create()).setTheme(BadgeTheme.WARNING));

    self.add(allThemesRow);
  }
}
