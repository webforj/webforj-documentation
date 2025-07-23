package com.webforj.samples.views.toolbar;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;

@Route
@FrameTitle("Toolbar Themes")
public class ToolbarThemeView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();

  public ToolbarThemeView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m) var(--dwc-space-m)");

    for (Theme theme : Theme.values()) {
      Toolbar toolbar = new Toolbar();
      toolbar
          .setTheme(theme)
          .addToTitle(new H1(theme.name()))
          .addToStart(new IconButton(TablerIcon.create("menu-2")))
          .addToEnd(
              new IconButton(TablerIcon.create("settings")),
              new IconButton(TablerIcon.create("user")));

      self.add(toolbar);
    }
  }
}
