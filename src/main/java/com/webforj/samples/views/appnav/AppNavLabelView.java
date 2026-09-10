package com.webforj.samples.views.appnav;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H1;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.appnav.AppNavLabel;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;

@Route
public class AppNavLabelView extends Composite<AppLayout> {
  private final AppLayout self = getBoundComponent();
  private final Toolbar toolbar = new Toolbar();
  private final AppNav appNav = new AppNav();

  public AppNavLabelView() {
    setHeader();
    setDrawer();
  }

  private void setHeader() {
    self.setHeaderOffscreen(false).setDrawerHeaderVisible(true).addToHeader(toolbar);
    toolbar.setTheme(Theme.PRIMARY).addToStart(new AppDrawerToggle()).addToTitle(new H1("Admin"));
  }

  private void setDrawer() {
    self.addToDrawer(appNav);

    appNav.addItem(createItem("Dashboard", "layout-dashboard"));

    AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
    analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.PRIMARY));
    appNav.add(analytics);

    appNav.addItem(createItem("Overview", "eye"));
    appNav.addItem(createItem("Reports", "chart-bar"));

    appNav.add(new AppNavLabel("Commerce", TablerIcon.create("shopping-bag")));
    appNav.addItem(createItem("Orders", "shopping-cart"));
    appNav.addItem(createItem("Products", "box"));
    appNav.addItem(createItem("Customers", "address-book"));

    appNav.add(new AppNavLabel("Account", TablerIcon.create("user-cog")));
    appNav.addItem(createItem("Profile", "user"));
    appNav.addItem(createItem("Billing", "credit-card"));
  }

  private AppNavItem createItem(String text, String icon) {
    return new AppNavItem(
        text, AppNavLabelPageView.class, ParametersBag.of("id=" + text), TablerIcon.create(icon));
  }
}
