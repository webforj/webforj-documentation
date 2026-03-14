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
  private final FlexLayout self = getBoundComponent();

  public IconBasicsView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.ROW)
        .setMargin("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-m)");

    // Create icons using Tabler Icons
    Icon message = TablerIcon.create("message");
    Icon trash = TablerIcon.create("trash");
    Icon edit = TablerIcon.create("edit");

    self.add(message, trash, edit);
  }
}

