package com.webforj.samples.views.applayout.stickytoolbar;

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
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import com.webforj.samples.views.applayout.DrawerLogo;

@StyleSheet("ws://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout Sticky Toolbar")
public class AppLayoutStickyToolbarView extends Composite<AppLayout> {
  AppLayout self = getBoundComponent();
  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutStickyToolbarView() {
    self.setDrawerHeaderVisible(false);
    self.addClassName("layout--collapse");

    // Header
    header.addToStart(new AppDrawerToggle())
        .addToTitle(new H3("Application"));
    self.addToHeader(header);
    self.setHeaderFixed(false);
    self.setHeaderReveal(true);

    // Drawer
    self.addToDrawer(new DrawerLogo(), drawer);

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

    // Adding the additional toolbar with menu items
    Toolbar secondToolbar = new Toolbar();
    secondToolbar.setCompact(true);
    self.addToHeader(secondToolbar);

    TabbedPane secondMenu = new TabbedPane();
    secondToolbar.addToStart(secondMenu);
    secondMenu.setBorderless(true);
    secondMenu.setBodyHidden(true);

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
