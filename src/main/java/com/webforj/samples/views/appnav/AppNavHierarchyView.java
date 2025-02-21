package com.webforj.samples.views.appnav;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@InlineStyleSheet("context://css/applayout/applayout.css")
@Route
@FrameTitle("Hierarchical AppNav Demo")
public class AppNavHierarchyView extends Composite<AppLayout> {
  AppLayout demo = getBoundComponent();
  Paragraph contentLabel = new Paragraph();

  Div header = new Div();
  Div drawer = new Div();

  public AppNavHierarchyView() {
    demo.setDrawerHeaderVisible(true);
    demo.setDrawerFooterVisible(true);
    demo.setDrawerOpened(true);
    
    demo.addToDrawerTitle(new Div("Menu"));
    demo.addToDrawerHeaderActions(new Element("dwc-icon-button")
        .setAttribute("name", "pin"));
    demo.addToDrawerFooter(new Paragraph("All rights reserved"));

    // Header
    header.addClassName("layout__header").add(
        new AppDrawerToggle(),
        new Paragraph("WebforJ Application").setStyle("font-size", "1.25rem") 
        .setStyle("font-weight", "bold") 
        .setStyle("margin", "var(--dwc-space-m)"));
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
    Icon settingsIcon = TablerIcon.create("settings");

    AppNavItem dashboardItem = new AppNavItem("Dashboard", "/appnav").setPrefixComponent(dashboardIcon);
    AppNavItem ordersItem = new AppNavItem("Orders").setPrefixComponent(ordersIcon);
    AppNavItem newOrdersItem = new AppNavItem("New Orders", "/appnavhierarchy");
    AppNavItem inProgressOrdersItem = new AppNavItem("Orders in Progress", "/appnavhierarchy");
    AppNavItem customersItem = new AppNavItem("Customers", "/appnav").setPrefixComponent(customersIcon);
    AppNavItem productsItem = new AppNavItem("Products", "/appnav").setPrefixComponent(productsIcon);
    AppNavItem documentsItem = new AppNavItem("Documents", "/appnav").setPrefixComponent(documentsIcon);
    AppNavItem tasksItem = new AppNavItem("Tasks", "/appnav").setPrefixComponent(tasksIcon); 
    AppNavItem settingsItem = new AppNavItem("Settings").setPrefixComponent(settingsIcon);
    AppNavItem accountSettingsItem = new AppNavItem("Account Settings", "/appnavhierarchy");
    AppNavItem preferencesItem = new AppNavItem("Preferences", "/appnavhierarchy");
    
    ordersItem.addItem(newOrdersItem);
    ordersItem.addItem(inProgressOrdersItem);
    
    settingsItem.addItem(accountSettingsItem);
    settingsItem.addItem(preferencesItem);
    
    drawerMenu.addItem(dashboardItem);
    drawerMenu.addItem(ordersItem);
    drawerMenu.addItem(customersItem);
    drawerMenu.addItem(productsItem);
    drawerMenu.addItem(documentsItem);
    drawerMenu.addItem(tasksItem);
    drawerMenu.addItem(settingsItem);

    // Content
    demo.addToContent(new H1("Application Title"), this.contentLabel);
  }
}
  
