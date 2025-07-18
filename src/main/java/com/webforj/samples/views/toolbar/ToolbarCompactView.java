package com.webforj.samples.views.toolbar;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;

@Route("toolbarcompact")
@FrameTitle("Toolbar Compact")
public class ToolbarCompactView extends Composite<AppLayout> {

  AppLayout self = getBoundComponent();

  public ToolbarCompactView() {
    self
        .setDrawerPlacement(AppLayout.DrawerPlacement.HIDDEN)
        .setStyle("--dwc-app-layout-header-height", "80px")
        .add(new H1("Application Title"), new Paragraph("Content goes here"));

    Toolbar mainToolbar = new Toolbar();
    mainToolbar
        .addToTitle(new H3("Application"))
        .addToStart(new AppDrawerToggle());

    Toolbar secondToolbar = new Toolbar();
    secondToolbar.setCompact(true);

    TabbedPane menu = new TabbedPane();
    menu
        .setBorderless(true)
        .setBodyHidden(true);

    menu.addTab(new Tab("Sales", TablerIcon.create("report-money")));
    menu.addTab(new Tab("Enterprise", TablerIcon.create("building")));
    menu.addTab(new Tab("Payments", TablerIcon.create("credit-card")));
    menu.addTab(new Tab("History", TablerIcon.create("history")));

    secondToolbar.add(menu);

    self.addToHeader(mainToolbar, secondToolbar);
  }
}
