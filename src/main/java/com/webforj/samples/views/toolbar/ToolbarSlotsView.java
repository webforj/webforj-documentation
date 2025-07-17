package com.webforj.samples.views.toolbar;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.toolbar.Toolbar;

@Route
@FrameTitle("Toolbar Slots")
@StyleSheet(
/* css */"""
      dwc-toolbar {
      --dwc-toolbar-background: hsl(265, 100%, 47%);
      --dwc-toolbar-color: white;
      --dwc-icon-button-hover-color: var(--dwc-toolbar-color);
      --dwc-icon-button-active-color: var(--dwc-toolbar-color);
    }
    """)
public class ToolbarSlotsView extends Composite<AppLayout> {

  AppLayout self = getBoundComponent();

  public ToolbarSlotsView() {
    self
        .setDrawerPlacement(AppLayout.DrawerPlacement.HIDDEN)
        .setStyle("--dwc-app-layout-header-height", "52px")
        .add(new H1("Application Title"), new Paragraph("Content goes here"));

    Toolbar toolbar = new Toolbar();
    toolbar
        .addToTitle(new H3("Application"))
        .addToStart(new IconButton(TablerIcon.create("menu-2")))
        .addToEnd(
            new IconButton(TablerIcon.create("settings")),
            new IconButton(TablerIcon.create("user")))
        .addToContent(new H3("Toolbar Content"));

    self.addToHeader(toolbar);
  }
}
