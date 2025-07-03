package com.webforj.samples.views.drawer;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/drawer/drawerWelcome.css")
@Route(RouteConfig.DRAWER_WELCOME)
@FrameTitle("Drawer Welcome App")
public class DrawerWelcomeView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  Drawer welcomeDrawer;
  AppLayout demo = new AppLayout();
  Div header = new Div();
  Div navigation = new Div();

  public DrawerWelcomeView() {
    layout.add(demo);

    // Header
    H3 title = new H3("webforJ Application");
    header.add(new AppDrawerToggle(), title);
    header.addClassName("dwc__toolbar-drawer");

    demo.addToHeader(header);
    demo.setHeaderReveal(true);

    demo.addToDrawer(navigation);
    demo.setDrawerPlacement(DrawerPlacement.LEFT);

    // Drawer's logo container and logo
    Toolbar drawerLogo = new Toolbar();
    drawerLogo.addClassName("dwc-logo").setWidth("100%")
        .add(new Img("https://docs.webforj.com/img/webforj_icon.svg"));
    navigation.add(drawerLogo);

    // Drawer's Menu with AppNav
    AppNav appNav = new AppNav();
    appNav.setAutoOpen(true);
    navigation.add(appNav);

    // Adding navigation items
    AppNavItem dashboardItem = new AppNavItem("Dashboard", "/drawerwelcome");
    dashboardItem.setPrefixComponent(TablerIcon.create("dashboard"));
    AppNavItem ordersItem = new AppNavItem("Orders", "/drawerwelcome");
    ordersItem.setPrefixComponent(TablerIcon.create("shopping-cart"));
    AppNavItem customersItem = new AppNavItem("Customers", "/drawerwelcome");
    customersItem.setPrefixComponent(TablerIcon.create("users"));
    AppNavItem productsItem = new AppNavItem("Products", "/drawerwelcome");
    productsItem.setPrefixComponent(TablerIcon.create("box"));
    AppNavItem documentsItem = new AppNavItem("Documents", "/drawerwelcome");
    documentsItem.setPrefixComponent(TablerIcon.create("files"));

    appNav.addItem(dashboardItem);
    appNav.addItem(ordersItem);
    appNav.addItem(customersItem);
    appNav.addItem(productsItem);
    appNav.addItem(documentsItem);

    // Welcome Drawer
    welcomeDrawer = new Drawer();
    layout.add(welcomeDrawer);

    welcomeDrawer.setPlacement(Placement.BOTTOM)
      .addClassName("welcome__drawer")
        .open();

    Button getStarted = new Button("Get Started").setTheme(ButtonTheme.PRIMARY);
    getStarted.onClick(e -> welcomeDrawer.close());

    FlexLayout layout = FlexLayout.create(new Img("/fun.svg")
        .setSize("200px", "200px"),
      new H2("Welcome to webforJ"),
      new Paragraph("Lorem Ipsum is simply dummy text of the printing and typesetting industry")
          .setStyle("text-align", "center"),
        getStarted)
        .vertical()
        .align().center()
        .justify().center()
        .build();

    welcomeDrawer.add(layout);

    // Content
    Button openWelcome = new Button("Open Welcome Drawer");
    openWelcome.onClick(e -> welcomeDrawer.open());

    demo.addToContent(new H1("Application Title"), openWelcome);
  }
}