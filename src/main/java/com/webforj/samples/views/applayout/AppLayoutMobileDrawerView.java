package com.webforj.samples.views.applayout;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Strong;
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
import com.webforj.samples.views.applayout.applayoutmobiledrawer.AnalyticsView;
import com.webforj.samples.views.applayout.applayoutmobiledrawer.CustomersView;
import com.webforj.samples.views.applayout.applayoutmobiledrawer.DashboardView;
import com.webforj.samples.views.applayout.applayoutmobiledrawer.DocumentsView;
import com.webforj.samples.views.applayout.applayoutmobiledrawer.OrdersView;
import com.webforj.samples.views.applayout.applayoutmobiledrawer.ProductsView;
import com.webforj.samples.views.applayout.applayoutmobiledrawer.TasksView;

@InlineStyleSheet("context://css/applayout/applayoutMobile.css")
@Route
@FrameTitle("AppLayout Mobile Drawer")
public class AppLayoutMobileDrawerView extends Composite<AppLayout> {

  AppLayout demo = getBoundComponent();
  Div contentLabel = new Div();
  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutMobileDrawerView() {
    // Header
    AppDrawerToggle toggle = new AppDrawerToggle();
    Div logo = new Div();
    logo.addClassName("dwc-logo").add(
        new Img("https://documentation.webforj.com/img/webforj_icon.svg", "logo"));
    Strong title = new Strong("Application");

    header.addToStart(toggle).addToTitle(logo, title);
    header.addClassName("drawer__dwc-toolbar");

    demo.addToHeader(header);
    demo.setHeaderReveal(true);

    // Drawer
    demo.addToDrawer(drawer);
    drawer.addClassName("app-layout-drawer");

    // Drawer's logo container and logo
    Div drawerLogo = new Div();
    drawerLogo.addClassName("drawer__logo")
        .add(new Img("https://documentation.webforj.com/img/webforj_icon.svg", "logo"));
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

    drawerMenu.addItem(new AppNavItem("Dashboard", DashboardView.class, dashboardIcon));
    drawerMenu.addItem(new AppNavItem("Orders", OrdersView.class, ordersIcon));
    drawerMenu.addItem(new AppNavItem("Customers", CustomersView.class, customersIcon));
    drawerMenu.addItem(new AppNavItem("Products", ProductsView.class, productsIcon));
    drawerMenu.addItem(new AppNavItem("Documents", DocumentsView.class, documentsIcon));
    drawerMenu.addItem(new AppNavItem("Tasks", TasksView.class, tasksIcon));
    drawerMenu.addItem(new AppNavItem("Analytics", AnalyticsView.class, analyticsIcon));
    
    // Content
    demo.addToContent(
        new H1("Application Title"),
        this.contentLabel);

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
