package com.webforj.samples.views.applayout.fullnavbar;

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
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;

@InlineStyleSheet("context://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout Full Navbar")
public class AppLayoutFullNavbarView extends Composite<AppLayout> {

  AppLayout demo = getBoundComponent();
  Paragraph contentLabel = new Paragraph();

  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutFullNavbarView() {
    demo.setDrawerHeaderVisible(false);
    demo.setDrawerFooterVisible(true);

    demo.addToDrawerFooter(new Paragraph("All rights reserved"));

    // Header
    header.addClassName("layout__header").addToStart(
        new AppDrawerToggle()).addToTitle(
            new H3("WebforJ Application"));
    demo.addToHeader(header);
    demo.setHeaderOffscreen(false);

    // Drawer
    demo.addToDrawer(drawer);
    drawer.addClassName("app-layout-drawer");

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
        new AppNavItem("Dashboard", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Dashboard"),
            dashboardIcon));
    drawerMenu
        .addItem(new AppNavItem("Orders", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Orders"),
            ordersIcon));
    drawerMenu.addItem(
        new AppNavItem("Customers", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Customers"),
            customersIcon));
    drawerMenu.addItem(
        new AppNavItem("Products", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Products"),
            productsIcon));
    drawerMenu.addItem(
        new AppNavItem("Documents", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Documents"),
            documentsIcon));
    drawerMenu
        .addItem(
            new AppNavItem("Tasks", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Tasks"), tasksIcon));
    drawerMenu.addItem(
        new AppNavItem("Analytics", AppLayoutFullNavbarContentView.class, ParametersBag.of("name=Analytics"),
            analyticsIcon));

    // Content
    demo.addToContent(new H1("Application Title"), this.contentLabel);
  }

}
