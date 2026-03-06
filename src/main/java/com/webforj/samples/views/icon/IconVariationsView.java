package com.webforj.samples.views.icon;

import com.webforj.component.Composite;
import com.webforj.component.icons.FontAwesomeIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Icon Variations")
public class IconVariationsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public IconVariationsView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.ROW)
        .setMargin("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-m)");

    // Font Awesome icons with different variations
    Icon mail = FontAwesomeIcon.create("envelope");
    Icon mailSolid = FontAwesomeIcon.create("envelope", FontAwesomeIcon.Variate.SOLID);
    Icon instagram = FontAwesomeIcon.create("instagram", FontAwesomeIcon.Variate.BRAND);

    // Tabler icons with different variations
    Icon calendar = TablerIcon.create("calendar");
    Icon calendarFilled = TablerIcon.create("calendar", TablerIcon.Variate.FILLED);

    self.add(mail, mailSolid, instagram, calendar, calendarFilled);
  }
}