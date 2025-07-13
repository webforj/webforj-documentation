package com.webforj.samples.views.toolbar;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TOOLBAR_PROGRESS_BAR)
@FrameTitle("Progressbar in Toolbar")
public class ToolbarProgressbarView extends Composite<AppLayout> {

  AppLayout self = getBoundComponent();

  public ToolbarProgressbarView() {
    self
        .setDrawerPlacement(AppLayout.DrawerPlacement.HIDDEN)
        .setStyle("--dwc-app-layout-header-height", "52px")
        .add(new H1("Application Title"), new Paragraph("Content goes here"));

    ProgressBar progressBar = new ProgressBar();
    progressBar.setStriped(true)
        .setAnimated(true)
        .setValue(75)
        .setTheme(Theme.PRIMARY)
        .setStyle("--dwc-progressbar-height", "5px");

    Toolbar toolbar = new Toolbar();
    toolbar
        .addToTitle(new H3("Application"))
        .addToStart(new IconButton(TablerIcon.create("menu-2")))
        .add(progressBar);

    self.addToHeader(toolbar);
  }
}
