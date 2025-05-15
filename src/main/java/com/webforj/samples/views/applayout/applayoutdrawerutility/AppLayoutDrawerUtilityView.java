package com.webforj.samples.views.applayout.applayoutdrawerutility;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
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

@InlineStyleSheet("context://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout")
public class AppLayoutDrawerUtilityView extends Composite<AppLayout> {

  AppLayout self = getBoundComponent();
  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutDrawerUtilityView() {

    // Header
    header.addToStart(new AppDrawerToggle())
        .addToTitle(new H3("Application"));
    self.addToHeader(header);

    // Drawer
    self.addToDrawer(drawer);
    self.addToDrawerTitle(new Div("Joe Smith"));
    self.addToDrawerHeaderActions(new IconButton("pin", "tabler").setTooltipText("Pin drawer"),
        new IconButton("rocket", "tabler").setTooltipText("Buy premium"));
    self.setDrawerHeaderVisible(true);
    self.setDrawerOpened(true);

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
        new AppNavItem("Dashboard", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Dashboard"),
            dashboardIcon));
    drawerMenu
        .addItem(new AppNavItem("Orders", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Orders"),
            ordersIcon));
    drawerMenu.addItem(
        new AppNavItem("Customers", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Customers"),
            customersIcon));
    drawerMenu.addItem(
        new AppNavItem("Products", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Products"),
            productsIcon));
    drawerMenu.addItem(
        new AppNavItem("Documents", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Documents"),
            documentsIcon));
    drawerMenu
        .addItem(new AppNavItem("Tasks", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Tasks"),
            tasksIcon));
    drawerMenu.addItem(
        new AppNavItem("Analytics", AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=Analytics"),
            analyticsIcon));

  }
}
