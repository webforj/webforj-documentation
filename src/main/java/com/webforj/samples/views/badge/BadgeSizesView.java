package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
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
    row.setWrap(FlexWrap.WRAP);
    row.setAlignment(FlexAlignment.CENTER);

    Badge b3xs = new Badge("3xs");
    b3xs.setTheme(BadgeTheme.PRIMARY);
    b3xs.setExpanse(BadgeExpanse.XXXSMALL);
    Badge b2xs = new Badge("2xs");
    b2xs.setTheme(BadgeTheme.PRIMARY);
    b2xs.setExpanse(BadgeExpanse.XXSMALL);
    Badge bxs = new Badge("xs");
    bxs.setTheme(BadgeTheme.PRIMARY);
    bxs.setExpanse(BadgeExpanse.XSMALL);
    Badge bs = new Badge("s");
    bs.setTheme(BadgeTheme.PRIMARY);
    bs.setExpanse(BadgeExpanse.SMALL);
    Badge bm = new Badge("m");
    bm.setTheme(BadgeTheme.PRIMARY);
    bm.setExpanse(BadgeExpanse.MEDIUM);
    Badge bl = new Badge("l");
    bl.setTheme(BadgeTheme.PRIMARY);
    bl.setExpanse(BadgeExpanse.LARGE);
    Badge bxl = new Badge("xl");
    bxl.setTheme(BadgeTheme.PRIMARY);
    bxl.setExpanse(BadgeExpanse.XLARGE);
    Badge b2xl = new Badge("2xl");
    b2xl.setTheme(BadgeTheme.PRIMARY);
    b2xl.setExpanse(BadgeExpanse.XXLARGE);
    Badge b3xl = new Badge("3xl");
    b3xl.setTheme(BadgeTheme.PRIMARY);
    b3xl.setExpanse(BadgeExpanse.XXXLARGE);

    row.add(b3xs, b2xs, bxs, bs, bm, bl, bxl, b2xl, b3xl);

    self.add(row);

    self.add(new H3("Single Character (circular)"));

    FlexLayout circleRow = FlexLayout.create().horizontal().build();
    circleRow.setSpacing("var(--dwc-space-s)");
    circleRow.setAlignment(FlexAlignment.CENTER);

    Badge cxs = new Badge("5");
    cxs.setTheme(BadgeTheme.DANGER);
    cxs.setExpanse(BadgeExpanse.XSMALL);
    Badge cs = new Badge("5");
    cs.setTheme(BadgeTheme.DANGER);
    cs.setExpanse(BadgeExpanse.SMALL);
    Badge cm = new Badge("5");
    cm.setTheme(BadgeTheme.DANGER);
    cm.setExpanse(BadgeExpanse.MEDIUM);
    Badge cl = new Badge("5");
    cl.setTheme(BadgeTheme.DANGER);
    cl.setExpanse(BadgeExpanse.LARGE);
    Badge cxl = new Badge("5");
    cxl.setTheme(BadgeTheme.DANGER);
    cxl.setExpanse(BadgeExpanse.XLARGE);

    circleRow.add(cxs, cs, cm, cl, cxl);

    self.add(circleRow);
  }
}
