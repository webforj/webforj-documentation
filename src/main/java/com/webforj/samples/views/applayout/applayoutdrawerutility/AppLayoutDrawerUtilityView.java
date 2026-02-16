package com.webforj.samples.views.applayout.applayoutdrawerutility;

import com.webforj.annotation.StyleSheet;
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

@StyleSheet("ws://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout")
public class AppLayoutDrawerUtilityView extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private Toolbar header = new Toolbar();
  private Div drawer = new Div();
  private AppNav drawerMenu = new AppNav();

  public AppLayoutDrawerUtilityView() {
    // Header
    header.addToStart(new AppDrawerToggle())
            .addToTitle(new H3("Application"));
    self.addToHeader(header);

    // Drawer
    self.addToDrawer(drawer)
            .addToDrawerTitle(new Div("Joe Smith"))
            .addToDrawerHeaderActions(new IconButton("pin", "tabler").setTooltipText("Pin drawer"),
                    new IconButton("rocket", "tabler").setTooltipText("Buy premium"))
            .setDrawerHeaderVisible(true)
            .setDrawerOpened(true);

    // Drawer's Menu
    drawer.add(new DrawerLogo(), drawerMenu);

    // Adding tabs to drawer menu
    Icon dashboardIcon = TablerIcon.create("dashboard");
    Icon ordersIcon = TablerIcon.create("shopping-cart");
    Icon customersIcon = TablerIcon.create("users");
    Icon productsIcon = TablerIcon.create("box");
    Icon documentsIcon = TablerIcon.create("files");
    Icon tasksIcon = TablerIcon.create("checklist");
    Icon analyticsIcon = TablerIcon.create("chart-dots-2");

    drawerMenu.addItem(createItem("Dashboard", dashboardIcon))
            .addItem(createItem("Orders", ordersIcon))
            .addItem(createItem("Customers", customersIcon))
            .addItem(createItem("Products", productsIcon))
            .addItem(createItem("Documents", documentsIcon))
            .addItem(createItem("Tasks", tasksIcon))
            .addItem(createItem("Analytics", analyticsIcon));
  }

  private AppNavItem createItem(String text, Icon icon) {
    return new AppNavItem(text, AppLayoutDrawerUtilityContentView.class, ParametersBag.of("name=" + text), icon);
  }
}
