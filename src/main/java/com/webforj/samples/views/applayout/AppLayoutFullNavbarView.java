package com.webforj.samples.views.applayout;


import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
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

@InlineStyleSheet("context://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout Full Navbar")
public class AppLayoutFullNavbarView extends Composite<AppLayout> {

  AppLayout demo = getBoundComponent();
  Paragraph contentLabel = new Paragraph();

  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutFullNavbarView() {
    demo.setDrawerHeaderVisible(true);
    demo.setDrawerFooterVisible(true);
    demo.addToDrawerTitle(new Div("Menu"));
    demo.addToDrawerHeaderActions(new Element("dwc-icon-button")
        .setAttribute("name", "pin"));
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

    // Drawer's logo container and logo
    Div drawerLogo = new Div();
    drawerLogo.addClassName("drawer__logo")
        .add(new Img("https://documentation.webforj.com/img/webforj_icon.svg"));
    drawer.add(drawerLogo);

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

    drawerMenu.addItem(new AppNavItem("Dashboard", "webforj/applayoutfullnavbar/content/Dashboard", dashboardIcon));
    drawerMenu.addItem(new AppNavItem("Orders", "webforj/applayoutfullnavbar/content/Orders", ordersIcon));
    drawerMenu.addItem(new AppNavItem("Customers", "webforj/applayoutfullnavbar/content/Customers", customersIcon));
    drawerMenu.addItem(new AppNavItem("Products", "webforj/applayoutfullnavbar/content/Products", productsIcon));
    drawerMenu.addItem(new AppNavItem("Documents", "webforj/applayoutfullnavbar/content/Documents", documentsIcon));
    drawerMenu.addItem(new AppNavItem("Tasks", "webforj/applayoutfullnavbar/content/Tasks", tasksIcon));
    drawerMenu.addItem(new AppNavItem("Analytics", "webforj/applayoutfullnavbar/content/Analytics", analyticsIcon));

    // Content
    demo.addToContent(new H1("Application Title"), this.contentLabel);
  }

}

