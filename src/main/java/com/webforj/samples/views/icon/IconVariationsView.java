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

  public IconVariationsView() {
    getBoundComponent().setDirection(FlexDirection.ROW).setMargin("var(--dwc-space-l)")
    .setSpacing("var(--dwc-space-m)");

    Icon mail = FontAwesomeIcon.create("envelope");        
    Icon mailsolid = FontAwesomeIcon.create("envelope", FontAwesomeIcon.Variate.SOLID);
        
    Icon instagram = FontAwesomeIcon.create("instagram", FontAwesomeIcon.Variate.BRAND);
        
    Icon calendar = TablerIcon.create("calendar");       
    Icon calendarfilled = TablerIcon.create("calendar", TablerIcon.Variate.FILLED);    

    getBoundComponent().add(mail, mailsolid, instagram, calendar, calendarfilled);
  }
}