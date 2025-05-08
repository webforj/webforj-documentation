package com.webforj.samples.views.applayout.stickytoolbar;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
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
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;

@InlineStyleSheet("context://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout Sticky Toolbar")
public class AppLayoutStickyToolbarView extends Composite<AppLayout> {

  AppLayout demo = getBoundComponent();
  Paragraph contentLabel = new Paragraph();

  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutStickyToolbarView() {

    demo.addToDrawerFooter(new Paragraph("All rights reserved"));
    demo.setDrawerHeaderVisible(false);
    demo.addClassName("layout_header_collapse");
    // Header
    header.addClassName("layout__header").addToStart(
        new AppDrawerToggle()).addToTitle(
            new H3("Application"));
    demo.addToHeader(header);
    demo.setHeaderFixed(false);
    demo.setHeaderReveal(true);

    // Drawer
    demo.addToDrawer(drawer);
    drawer.addClassName("app-layout-drawer");

    // Drawer's logo container and logo
    Div drawerLogo = new Div();
    drawerLogo.addClassName("drawer__logo")
        .add(new Img("../img/webforj_icon.svg", "logo"));
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

    drawerMenu.addItem(
        new AppNavItem("Dashboard", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Dashboard"),
            dashboardIcon));
    drawerMenu
        .addItem(new AppNavItem("Orders", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Orders"),
            ordersIcon));
    drawerMenu.addItem(
        new AppNavItem("Customers", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Customers"),
            customersIcon));
    drawerMenu.addItem(
        new AppNavItem("Products", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Products"),
            productsIcon));
    drawerMenu.addItem(
        new AppNavItem("Documents", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Documents"),
            documentsIcon));
    drawerMenu
        .addItem(new AppNavItem("Tasks", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Tasks"),
            tasksIcon));
    drawerMenu.addItem(
        new AppNavItem("Analytics", AppLayoutStickyToolbarContentView.class, ParametersBag.of("name=Analytics"),
            analyticsIcon));

    // Content
    demo.addToContent(
        new H1("Application Title"),
        this.contentLabel);

    // Adding the additional toolbar with menu items
    Toolbar secondToolbar = new Toolbar();
    secondToolbar.addClassName("layout__second__header");
    demo.addToHeader(secondToolbar);
    TabbedPane secondMenu = new TabbedPane();
    secondToolbar.add(secondMenu);
    secondMenu.setBorderless(true);
    secondToolbar.setAttribute("position", "sticky");

    Icon salesIcon = TablerIcon.create("report-money");
    Icon enterpriseIcon = TablerIcon.create("building");
    Icon paymentsIcon = TablerIcon.create("credit-card");
    Icon historyIcon = TablerIcon.create("history");

    secondMenu.addTab(new Tab("Sales", salesIcon));
    secondMenu.addTab(new Tab("Enterprise", enterpriseIcon));
    secondMenu.addTab(new Tab("Payments", paymentsIcon));
    secondMenu.addTab(new Tab("History", historyIcon));

  }
}
