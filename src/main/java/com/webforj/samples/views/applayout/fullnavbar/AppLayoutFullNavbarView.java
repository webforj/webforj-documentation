package com.webforj.samples.views.applayout.fullnavbar;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
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
import com.webforj.samples.views.applayout.applayoutdrawerutility.AppLayoutDrawerUtilityContentView;

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout Full Navbar")
public class AppLayoutFullNavbarView extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private Toolbar header = new Toolbar();
  private AppNav drawerMenu = new AppNav();

  public AppLayoutFullNavbarView() {
    self.setDrawerHeaderVisible(false);
    self.setDrawerFooterVisible(true);
    // Header
    header.addToStart(new AppDrawerToggle())
        .addToTitle(new H3(" Application"));
    self.addToHeader(header);
    self.setHeaderOffscreen(false);

    // Drawer's Menu
    self.addToDrawer(drawerMenu);

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
    return new AppNavItem(text, AppLayoutFullNavbarContentView.class, ParametersBag.of("name=" + text), icon);
  }
}
