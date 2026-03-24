package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
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
    iconTextRow.setWrap(FlexWrap.WRAP);
    iconTextRow.setAlignment(FlexAlignment.CENTER);

    Badge doneBadge = new Badge("Done", FeatherIcon.CHECK_CIRCLE.create());
    doneBadge.setTheme(BadgeTheme.SUCCESS);
    Badge errorBadge = new Badge("Error", FeatherIcon.X_CIRCLE.create());
    errorBadge.setTheme(BadgeTheme.DANGER);
    Badge infoBadge = new Badge("Info", FeatherIcon.INFO.create());
    infoBadge.setTheme(BadgeTheme.PRIMARY);
    Badge warningBadge = new Badge("Warning", FeatherIcon.ALERT_TRIANGLE.create());
    warningBadge.setTheme(BadgeTheme.WARNING);

    iconTextRow.add(doneBadge, errorBadge, infoBadge, warningBadge);

    self.add(iconTextRow);

    self.add(new H3("Icon Only"));

    FlexLayout iconRow = FlexLayout.create().horizontal().build();
    iconRow.setSpacing("var(--dwc-space-s)");
    iconRow.setAlignment(FlexAlignment.CENTER);

    Badge checkBadge = new Badge(FeatherIcon.CHECK.create());
    checkBadge.setTheme(BadgeTheme.SUCCESS);
    Badge xBadge = new Badge(FeatherIcon.X.create());
    xBadge.setTheme(BadgeTheme.DANGER);
    Badge bellBadge = new Badge(FeatherIcon.BELL.create());
    bellBadge.setTheme(BadgeTheme.PRIMARY);
    Badge starBadge = new Badge(FeatherIcon.STAR.create());
    starBadge.setTheme(BadgeTheme.GRAY);

    iconRow.add(checkBadge, xBadge, bellBadge, starBadge);

    self.add(iconRow);

    self.add(new H3("All Themes with Icon + Text"));

    FlexLayout allThemesRow = FlexLayout.create().horizontal().build();
    allThemesRow.setSpacing("var(--dwc-space-s)");
    allThemesRow.setWrap(FlexWrap.WRAP);
    allThemesRow.setAlignment(FlexAlignment.CENTER);

    Badge newBadge = new Badge("New", FeatherIcon.STAR.create());
    newBadge.setTheme(BadgeTheme.PRIMARY);
    Badge liveBadge = new Badge("Live", FeatherIcon.RADIO.create());
    liveBadge.setTheme(BadgeTheme.SUCCESS);
    Badge alertBadge = new Badge("Alert", FeatherIcon.ALERT_CIRCLE.create());
    alertBadge.setTheme(BadgeTheme.DANGER);
    Badge draftBadge = new Badge("Draft", FeatherIcon.EDIT.create());
    draftBadge.setTheme(BadgeTheme.GRAY);
    Badge betaBadge = new Badge("Beta", FeatherIcon.ZAP.create());
    betaBadge.setTheme(BadgeTheme.WARNING);

    allThemesRow.add(newBadge, liveBadge, alertBadge, draftBadge, betaBadge);

    self.add(allThemesRow);
  }
}
