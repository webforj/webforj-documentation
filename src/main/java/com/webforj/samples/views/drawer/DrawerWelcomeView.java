package com.webforj.samples.views.drawer;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/drawer/drawerWelcome.css")
@Route
@FrameTitle("Drawer Welcome App")
public class DrawerWelcomeView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Drawer welcomeDrawer;
  private AppLayout appLayout = new AppLayout();
  private Toolbar header = new Toolbar();
  private Div navigation = new Div();

  public DrawerWelcomeView() {
    self.add(appLayout);

    // Header
    H3 title = new H3("webforJ Application");
    header.addToStart(new AppDrawerToggle())
        .addToTitle(title);

    appLayout.addToHeader(header)
        .setHeaderReveal(true);

    appLayout.addToDrawer(navigation)
        .setDrawerPlacement(DrawerPlacement.LEFT);

    // Drawer's logo container and logo
    Toolbar drawerLogo = new Toolbar();
    drawerLogo.addClassName("webforJ-logo")
        .setWidth("100%")
        .add(new Img("ws://img/webforj_icon.svg", "logo"));
    navigation.add(drawerLogo);

    // Drawer's Menu with AppNav
    AppNav appNav = new AppNav()
        .setAutoOpen(true);
    navigation.add(appNav);

    // Adding navigation items
    AppNavItem dashboardItem = new AppNavItem("Dashboard", "/drawerwelcome");
    dashboardItem.setPrefixComponent(TablerIcon.create("dashboard"));
    AppNavItem ordersItem = new AppNavItem("Orders", "/drawerwelcome");
    ordersItem.setPrefixComponent(TablerIcon.create("shopping-cart"));
    AppNavItem customersItem = new AppNavItem("Customers", "/drawerwelcome");
    customersItem.setPrefixComponent(TablerIcon.create("users"));
    AppNavItem productsItem = new AppNavItem("Products", "/drawerwelcome");
    productsItem.setPrefixComponent(TablerIcon.create("box"));
    AppNavItem documentsItem = new AppNavItem("Documents", "/drawerwelcome");
    documentsItem.setPrefixComponent(TablerIcon.create("files"));

    appNav.addItem(dashboardItem);
    appNav.addItem(ordersItem);
    appNav.addItem(customersItem);
    appNav.addItem(productsItem);
    appNav.addItem(documentsItem);

    // Welcome Drawer
    welcomeDrawer = new Drawer();
    self.add(welcomeDrawer);

    welcomeDrawer.setPlacement(Placement.BOTTOM_CENTER)
        .addClassName("welcome__drawer")
        .open();

    Button getStarted = new Button("Get Started")
        .setTheme(ButtonTheme.PRIMARY);
    getStarted.onClick(e -> welcomeDrawer.close());

    FlexLayout layout = FlexLayout.create(new Img("/fun.svg", "A gathering of people.")
            .setSize("200px", "200px"),
        new H2("Welcome to webforJ"),
        new Paragraph("Lorem Ipsum is simply dummy text of the printing and typesetting industry")
            .setStyle("text-align", "center"),
        getStarted)
        .vertical()
        .align().center()
        .justify().center()
        .build();

    welcomeDrawer.add(layout);

    // Content
    Button openWelcome = new Button("Open Welcome Drawer");
    openWelcome.onClick(e -> welcomeDrawer.open());

    appLayout.addToContent(new H1("Application Title"), openWelcome);
  }
}