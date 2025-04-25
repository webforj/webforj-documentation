package com.webforj.samples.views.applayout.multipleheaders;

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
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@InlineStyleSheet("context://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout Multiple Headers")
public class AppLayoutMultipleHeadersView extends Composite<AppLayout> {

  AppLayout demo = getBoundComponent();
  Paragraph contentLabel = new Paragraph();

  Toolbar header = new Toolbar();
  Div drawer = new Div();

  public AppLayoutMultipleHeadersView() {

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

    // Drawer
    drawer.addClassName("app-layout-drawer");
    demo.addToDrawer(drawer);

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
 
	 drawerMenu.addItem(new AppNavItem("Dashboard", "webforj/applayoutmultipleheaders/content/Dashboard", dashboardIcon));
	 drawerMenu.addItem(new AppNavItem("Orders", "webforj/applayoutmultipleheaders/content/Orders", ordersIcon));
	 drawerMenu.addItem(new AppNavItem("Customers", "webforj/applayoutmultipleheaders/content/Customers", customersIcon));
	 drawerMenu.addItem(new AppNavItem("Products", "webforj/applayoutmultipleheaders/content/Products", productsIcon));
	 drawerMenu.addItem(new AppNavItem("Documents", "webforj/applayoutmultipleheaders/content/Documents", documentsIcon));
	 drawerMenu.addItem(new AppNavItem("Tasks", "webforj/applayoutmultipleheaders/content/Tasks", tasksIcon));
	 drawerMenu.addItem(new AppNavItem("Analytics", "webforj/applayoutmultipleheaders/content/Analytics", analyticsIcon));
	 

    // Adding the additional toolbar with menu items
    Toolbar secondToolbar = new Toolbar();
    secondToolbar.setCompact(true);
    secondToolbar.addClassName("layout__second__header");
    demo.addToHeader(secondToolbar);
    demo.setHeaderFixed(true);
    TabbedPane secondMenu = new TabbedPane();
    secondToolbar.addToStart(secondMenu);
    secondMenu.setBodyHidden(true);
    secondMenu.setBorderless(true);

    Icon salesIcon = TablerIcon.create("report-money");
    Icon enterpriseIcon = TablerIcon.create("building");
    Icon paymentsIcon = TablerIcon.create("credit-card");
    Icon historyIcon = TablerIcon.create("history");

    secondMenu.addTab(new Tab("Sales", salesIcon));
    secondMenu.addTab(new Tab("Enterprise", enterpriseIcon));
    secondMenu.addTab(new Tab("Payments", paymentsIcon));
    secondMenu.addTab(new Tab("History", historyIcon));

	 // Content
	 demo.addToContent(new H1("Application Title"), this.contentLabel);

  }
}