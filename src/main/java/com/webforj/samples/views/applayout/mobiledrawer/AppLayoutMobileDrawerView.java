package com.webforj.samples.views.applayout.mobiledrawer;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.component.tabbedpane.TabbedPane.Placement;
import com.webforj.component.tabbedpane.TabbedPane.Alignment;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import com.webforj.samples.config.RouteConfig;
import com.webforj.samples.views.applayout.DrawerLogo;

@Route(RouteConfig.APP_LAYOUT_MOBILE_DRAWER)
@InlineStyleSheet("context://css/applayout/applayout.css")
@FrameTitle("AppLayout Mobile Drawer")
public class AppLayoutMobileDrawerView extends Composite<AppLayout> {
  AppLayout self = getBoundComponent();
  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutMobileDrawerView() {
    // Header
    header.addToStart(new AppDrawerToggle())
        .addToTitle(new H3("Application"));

    self.addToHeader(header);
    self.setHeaderReveal(true);

    // Drawer
    self.addToDrawer(drawer);
    drawer.addClassName("app-layout-drawer");

    // Drawer's logo container and logo
    drawer.add(new DrawerLogo());

    // Drawer's Menu
    AppNav drawerMenu = new AppNav();
    drawer.add(drawerMenu);

    // Adding tabs to drawer menu
    Icon dashboardIcon = TablerIcon.create("dashboard");
    Icon ordersIcon = TablerIcon.create("shopping-cart");
    Icon customersIcon = TablerIcon.create("users");
    Icon productsIcon = TablerIcon.create("box");
    Icon documentsIcon = TablerIcon.create("files");
    Icon tasksIcon = TablerIcon.create("checklist");
    Icon analyticsIcon = TablerIcon.create("chart-dots-2");

    drawerMenu.addItem(
        new AppNavItem("Dashboard", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Dashboard"),
            dashboardIcon));
    drawerMenu
        .addItem(new AppNavItem("Orders", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Orders"),
            ordersIcon));
    drawerMenu.addItem(
        new AppNavItem("Customers", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Customers"),
            customersIcon));
    drawerMenu.addItem(
        new AppNavItem("Products", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Products"),
            productsIcon));
    drawerMenu.addItem(
        new AppNavItem("Documents", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Documents"),
            documentsIcon));
    drawerMenu
        .addItem(
            new AppNavItem("Tasks", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Tasks"), tasksIcon));
    drawerMenu.addItem(
        new AppNavItem("Analytics", AppLayoutMobileDrawerContentView.class, ParametersBag.of("name=Analytics"),
            analyticsIcon));

    // Content
    self.addToContent(
        new H1("Application Title"),
        new Paragraph("Content goes here..."));

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
  }
}
