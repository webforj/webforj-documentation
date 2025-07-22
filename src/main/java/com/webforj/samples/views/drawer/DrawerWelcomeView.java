package com.webforj.samples.views.drawer;

import com.webforj.App;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Strong;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.text.Label;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.exceptions.WebforjException;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/drawer/drawerWelcome.css")
@Route("drawerwelcome")
@FrameTitle("Drawer Welcome App")
public class DrawerWelcomeView extends Composite<FlexLayout> {

	Drawer welcomeDrawer;
	AppLayout demo = new AppLayout();

	Div header = new Div();
	Div drawer = new Div();

	public DrawerWelcomeView() {
		getBoundComponent().add(demo);

		// Header

		Strong title = new Strong("DWCJ Application");
		header.add(new AppDrawerToggle(), title);
		header.addClassName("dwc__toolbar-drawer");

		demo.addToHeader(header);
		demo.setHeaderReveal(true);

		demo.addToDrawer(drawer);
		drawer.addClassName("app-layout-drawer");
		demo.setDrawerPlacement(DrawerPlacement.LEFT);

		// Drawer's logo container and logo
		Div drawerLogo = new Div();
		drawerLogo.addClassName("drawer__logo")
				.add(
						new Img("https://i.ibb.co/1n4n1Nh/logo.png\" alt=\"logo\""));
		drawer.add(drawerLogo);

		// Drawer's Menu
		TabbedPane drawerMenu = new TabbedPane();
		drawer.add(drawerMenu);


		drawerMenu.setBorderless(true);
		drawerMenu.setPlacement(TabbedPane.Placement.LEFT);

		// Adding tabs to drawer menu
		Icon dashboardIcon = TablerIcon.create("dashboard");
    Icon ordersIcon = TablerIcon.create("shopping-cart");
    Icon customersIcon = TablerIcon.create("users");
    Icon productsIcon = TablerIcon.create("box");
    Icon documentsIcon = TablerIcon.create("files");

    drawerMenu.addTab(new Tab("Dashboard", dashboardIcon));
    drawerMenu.addTab(new Tab("Orders", ordersIcon));
    drawerMenu.addTab(new Tab("Customers", customersIcon));
		drawerMenu.addTab(new Tab("Products", productsIcon));
    drawerMenu.addTab(new Tab("Documents", documentsIcon));

		// Welcome Drawer

		welcomeDrawer = new Drawer();
		getBoundComponent().add(welcomeDrawer);

		welcomeDrawer
				.setPlacement(Placement.BOTTOM)
				.addClassName("welcome__drawer")
				.open();

		FlexLayout layout = FlexLayout.create(new Img("""
				https://thumb9.shutterstock.com/mosaic_
				250/177370774/1312166426/stock-vector-handshake-heart-icon-stroke-outline-style
				-line-vector-isolate-on-white-background-1312166426.jpg
				"""),
				new H2("Welcome to DWCJ"),
				new Div("Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
				new Button("Get Started"))
				.vertical()
				.align().center()
				.build();
		welcomeDrawer.add(layout);
		// Content
		Button openWelcome = new Button("Open Welcome Page");
		openWelcome.onClick(e -> welcomeDrawer.open());

		demo.addToContent(
				new H1("Application Title"),
				new H4("Application Content"),
				openWelcome);
	}
}