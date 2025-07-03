package com.webforj.samples.views.applayout;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.component.tabbedpane.TabbedPane.Placement;
import com.webforj.component.tabbedpane.TabbedPane.Alignment;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.APP_LAYOUT_MOBILE_DRAWER)
@InlineStyleSheet("context://css/applayout/applayoutMobile.css")
@FrameTitle("AppLayout Mobile")
public class AppLayoutMobileView extends Composite<AppLayout> {
  AppLayout self = getBoundComponent();
  Toolbar header = new Toolbar();

  public AppLayoutMobileView() {
    // Header
    header
      .addToStart(TablerIcon.create("brand-whatsapp"))
      .addToTitle(new H3("Application"))
        .addClassName("drawer__dwc-toolbar")
        .setTheme(Theme.INFO);

    self.addToHeader(header);
    self.setHeaderReveal(true);
    self.setDrawerPlacement(DrawerPlacement.HIDDEN);

    TabbedPane footerMenu = new TabbedPane();
    self.addToFooter(footerMenu);
    self.setFooterReveal(true);

    footerMenu.setBodyHidden(true);
    footerMenu.setBorderless(true);
    footerMenu.setPlacement(Placement.BOTTOM);
    footerMenu.setAlignment(Alignment.STRETCH);

    // Adding tabs to drawer menu
    footerMenu.addTab(new Tab("", TablerIcon.create("dashboard")));
    footerMenu.addTab(new Tab("", TablerIcon.create("shopping-cart")));
    footerMenu.addTab(new Tab("", TablerIcon.create("users")));
    footerMenu.addTab(new Tab("", TablerIcon.create("box")));
    footerMenu.addTab(new Tab("", TablerIcon.create("files")));

    // Content
    for (int i = 0; i < 10; i++) {
      Div content = new Div();
      content.addClassName("card");
      content.add(
          new H2("What is Lorem Ipsum ?"),
          new Paragraph("Lorem Ipsum is simply dummy text of the printing and typesetting " +
              "industry. Lorem Ipsum has been the industry's standard dummy text " +
              "ever since the 1500s when an unknown printer took a galley of type " +
              "and scrambled it to make a type specimen book. It has survived not " +
              "only five centuries, but also the leap into electronic " +
              "typesetting, remaining essentially unchanged. It was popularized " +
              "in the 1960s with the release of Letraset sheets containing Lorem " +
              "Ipsum passages, and more recently with desktop publishing software " +
              "like Aldus PageMaker including versions of Lorem Ipsum."));
      self.addToContent(content);
    }
  }
}
