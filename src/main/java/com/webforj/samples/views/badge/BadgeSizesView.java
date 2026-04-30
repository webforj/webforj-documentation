package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Badge - Sizes")
public class BadgeSizesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BadgeSizesView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)")
        .setMargin("0 auto")
        .setMaxWidth("700px")
        .add(
            new H3("All Sizes"),
            createAllSizesRow(),
            new H3("Single Character (circular)"),
            createCircularRow());
  }

  private FlexLayout createAllSizesRow() {
    return FlexLayout.create(
            createBadge("3xs", BadgeExpanse.XXXSMALL),
            createBadge("2xs", BadgeExpanse.XXSMALL),
            createBadge("xs", BadgeExpanse.XSMALL),
            createBadge("s", BadgeExpanse.SMALL),
            createBadge("m", BadgeExpanse.MEDIUM),
            createBadge("l", BadgeExpanse.LARGE),
            createBadge("xl", BadgeExpanse.XLARGE),
            createBadge("2xl", BadgeExpanse.XXLARGE),
            createBadge("3xl", BadgeExpanse.XXXLARGE))
        .horizontal()
        .wrap()
        .build()
        .setSpacing("var(--dwc-space-s)")
        .setAlignment(FlexAlignment.CENTER);
  }

  private FlexLayout createCircularRow() {
    return FlexLayout.create(
            createBadge("5", BadgeExpanse.XSMALL),
            createBadge("5", BadgeExpanse.SMALL),
            createBadge("5", BadgeExpanse.MEDIUM),
            createBadge("5", BadgeExpanse.LARGE),
            createBadge("5", BadgeExpanse.XLARGE))
        .horizontal()
        .build()
        .setSpacing("var(--dwc-space-s)")
        .setAlignment(FlexAlignment.CENTER);
  }

  private Badge createBadge(String text, BadgeExpanse expanse) {
    return new Badge(text).setTheme(BadgeTheme.PRIMARY).setExpanse(expanse);
  }
}
