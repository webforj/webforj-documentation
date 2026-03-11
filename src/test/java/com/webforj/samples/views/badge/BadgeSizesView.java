package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/sizes")
@FrameTitle("Badge - Sizes")
public class BadgeSizesView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public BadgeSizesView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("All Sizes"));

    FlexLayout row = FlexLayout.create().horizontal().build();
    row.setSpacing("var(--dwc-space-s)");
    row.setStyle("flex-wrap", "wrap");
    row.setStyle("align-items", "center");

    row.add(
        new Badge("3xs").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.XXXSMALL),
        new Badge("2xs").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.XXSMALL),
        new Badge("xs").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.XSMALL),
        new Badge("s").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.SMALL),
        new Badge("m").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.MEDIUM),
        new Badge("l").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.LARGE),
        new Badge("xl").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.XLARGE),
        new Badge("2xl").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.XXLARGE),
        new Badge("3xl").setTheme(BadgeTheme.PRIMARY).setExpanse(BadgeExpanse.XXXLARGE));

    self.add(row);

    self.add(new H3("Single Character (circular)"));

    FlexLayout circleRow = FlexLayout.create().horizontal().build();
    circleRow.setSpacing("var(--dwc-space-s)");
    circleRow.setStyle("align-items", "center");

    circleRow.add(
        new Badge("5").setTheme(BadgeTheme.DANGER).setExpanse(BadgeExpanse.XSMALL),
        new Badge("5").setTheme(BadgeTheme.DANGER).setExpanse(BadgeExpanse.SMALL),
        new Badge("5").setTheme(BadgeTheme.DANGER).setExpanse(BadgeExpanse.MEDIUM),
        new Badge("5").setTheme(BadgeTheme.DANGER).setExpanse(BadgeExpanse.LARGE),
        new Badge("5").setTheme(BadgeTheme.DANGER).setExpanse(BadgeExpanse.XLARGE));

    self.add(circleRow);
  }
}
