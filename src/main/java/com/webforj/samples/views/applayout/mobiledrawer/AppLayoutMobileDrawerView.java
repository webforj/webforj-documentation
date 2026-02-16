package com.webforj.samples.views.applayout.mobiledrawer;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
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
import com.webforj.samples.views.applayout.DrawerLogo;
import com.webforj.samples.views.applayout.fullnavbar.AppLayoutFullNavbarContentView;

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout Mobile Drawer")
public class AppLayoutMobileDrawerView extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private Toolbar header = new Toolbar();
  private Div drawer = new Div();
  private AppNav drawerMenu = new AppNav();
  private TabbedPane footerMenu = new TabbedPane();

  public AppLayoutMobileDrawerView() {
    // Header
    header.addToStart(new AppDrawerToggle())
            .addToTitle(new H3("Application"));

    self.addToHeader(header);
    self.setHeaderReveal(true);

    // Drawer
    self.addToDrawer(drawer);
    drawer.addClassName("app-layout-drawer");

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

    // Content
    self.addToContent(
                    new H1("Application Title"),
                    new Paragraph("Content goes here..."))
            .addToFooter(footerMenu)
            .setFooterReveal(true);

    footerMenu.setBodyHidden(true)
            .setBorderless(true)
            .setPlacement(Placement.BOTTOM)
            .setAlignment(Alignment.STRETCH)
            .setExpanse(Expanse.XLARGE);

    // Adding tabs to drawer menu
    footerMenu.addTab(createTab("dashboard"));
    footerMenu.addTab(createTab("shopping-card"));
    footerMenu.addTab(createTab("users"));
    footerMenu.addTab(createTab("box"));
    footerMenu.addTab(createTab("files"));
  }


  private AppNavItem createItem(String text, Icon icon) {
    return new AppNavItem(text, AppLayoutFullNavbarContentView.class, ParametersBag.of("name=" + text), icon);
  }

  private Tab createTab(String icon) {
    return new Tab("", new IconButton(TablerIcon.create(icon)));
  }
}
