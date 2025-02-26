package com.webforj.samples.views.applayout;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Strong;
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

@InlineStyleSheet("context://css/applayout/applayoutMobile.css")
@Route
@FrameTitle("AppLayout Mobile")
public class AppLayoutMobileView extends Composite<AppLayout> {

  AppLayout demo = getBoundComponent();
  Div contentLabel = new Div();
  Toolbar header = new Toolbar();

  public AppLayoutMobileView() {
    // Header
    Div logo = new Div();
    logo.addClassName("dwc-logo")
        .add(new Img("https://documentation.webforj.com/img/webforj_icon.svg", "logo"));
    Strong title = new Strong("Application");

    header.addToStart(logo).addToTitle(title);
    header.addClassName("dwc-toolbar");

    demo.addToHeader(header);
    demo.setHeaderReveal(true);
    demo.setDrawerPlacement(DrawerPlacement.HIDDEN);

    // Content
    demo.addToContent(
        new H1("Application Title"),
        this.contentLabel);
    for (int i = 0; i < 5; i++) {
      Div content = new Div().addClassName("card");
      content.add(
          new H2("What is Lorem Ipsum " + i + "?"),
          new Paragraph("Lorem Ipsum is simply dummy text of the printing and typesetting " +
              "industry. Lorem Ipsum has been the industry's standard dummy text " +
              "ever since the 1500s when an unknown printer took a galley of type " +
              "and scrambled it to make a type specimen book. It has survived not " +
              "only five centuries, but also the leap into electronic " +
              "typesetting, remaining essentially unchanged. It was popularized " +
              "in the 1960s with the release of Letraset sheets containing Lorem " +
              "Ipsum passages, and more recently with desktop publishing software " +
              "like Aldus PageMaker including versions of Lorem Ipsum."));
      demo.addToContent(content);
    }

    TabbedPane footerMenu = new TabbedPane();
    demo.addToFooter(footerMenu);
    demo.setFooterReveal(true);

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
  }

}
