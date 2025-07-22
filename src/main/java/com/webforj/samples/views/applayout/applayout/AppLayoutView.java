package com.webforj.samples.views.applayout.applayout;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import com.webforj.samples.views.applayout.DrawerLogo;

@Route("applayout")
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout")
public class AppLayoutView extends Composite<AppLayout> {

  AppLayout self = getBoundComponent();
  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutView() {

    // Header
    header.addToStart(new AppDrawerToggle())
        .addToTitle(new H3("Application"));
    self.addToHeader(header);

    // Drawer
    self.addToDrawer(drawer);

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
        new AppNavItem("Dashboard", AppLayoutContentView.class, ParametersBag.of("name=Dashboard"), dashboardIcon));
    drawerMenu
        .addItem(new AppNavItem("Orders", AppLayoutContentView.class, ParametersBag.of("name=Orders"), ordersIcon));
    drawerMenu.addItem(
        new AppNavItem("Customers", AppLayoutContentView.class, ParametersBag.of("name=Customers"), customersIcon));
    drawerMenu.addItem(
        new AppNavItem("Products", AppLayoutContentView.class, ParametersBag.of("name=Products"), productsIcon));
    drawerMenu.addItem(
        new AppNavItem("Documents", AppLayoutContentView.class, ParametersBag.of("name=Documents"), documentsIcon));
    drawerMenu
        .addItem(new AppNavItem("Tasks", AppLayoutContentView.class, ParametersBag.of("name=Tasks"), tasksIcon));
    drawerMenu.addItem(
        new AppNavItem("Analytics", AppLayoutContentView.class, ParametersBag.of("name=Analytics"), analyticsIcon));
  }
}
