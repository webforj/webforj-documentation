package com.webforj.samples.views.icon;

import com.webforj.component.Composite;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Icon Basics")
public class IconBasicsView extends Composite<FlexLayout> {

  public IconBasicsView() {
    getBoundComponent().setDirection(FlexDirection.ROW).setMargin("var(--dwc-space-l)")
    .setSpacing("var(--dwc-space-m)");

    Icon message = TablerIcon.create("message");
        
    Icon trash = TablerIcon.create("trash");
        
    Icon edit = TablerIcon.create("edit");

    getBoundComponent().add(message, trash, edit);
  }
}

