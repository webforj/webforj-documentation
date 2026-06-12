package com.webforj.samples.views.appnav;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.H1;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;

@Route
public class AppNavSearchView extends Composite<AppLayout> {
  private final AppLayout self = getBoundComponent();
  private final Toolbar toolbar = new Toolbar();
  private final AppNav appNav = new AppNav();

  public AppNavSearchView() {
    setHeader();
    setPinning();
    setSearch();
    setDrawer();
  }

  private void setHeader() {
    self.setHeaderOffscreen(false).setDrawerHeaderVisible(true).addToHeader(toolbar);
    toolbar.setTheme(Theme.PRIMARY).addToStart(new AppDrawerToggle()).addToTitle(new H1("Admin"));
  }

  private void setPinning() {
    // Pinning on so pinned shortcuts stay visible while the user filters.
    appNav.getPinning().setEnabled(true).setTitle("Favorites");
  }

  private void setSearch() {
    appNav.getSearch()
        .setFieldVisible(true)
        .setPlaceholder("Search menu")
        .setEmptyMessage("No matching items");
  }

  private void setDrawer() {
    self.addToDrawer(appNav);

    AppNavItem dashboard = createItem("Dashboard", "layout-dashboard");
    dashboard.setPinned(true); // stays visible while searching

    AppNavItem invoices =
        new AppNavItem("Invoices")
            .setPrefixComponent(TablerIcon.create("file-invoice"))
            .addItem(createItem("Outstanding", "clock"))
            .addItem(createItem("Paid", "check"))
            .addItem(createItem("Drafts", "pencil"));

    AppNavItem settings =
        new AppNavItem("Settings")
            .setPrefixComponent(TablerIcon.create("settings"))
            .addItem(createItem("Profile", "user"))
            .addItem(createItem("Billing", "credit-card"))
            .addItem(createItem("Team", "users"));

    appNav
        .setAutoOpen(true)
        .addItem(dashboard)
        .addItem(createItem("Reports", "chart-bar"))
        .addItem(invoices)
        .addItem(createItem("Customers", "address-book"))
        .addItem(createItem("Orders", "shopping-cart"))
        .addItem(createItem("Products", "box"))
        .addItem(settings);
  }

  private AppNavItem createItem(String text, String icon) {
    return new AppNavItem(
        text, AppNavSearchPageView.class, ParametersBag.of("id=" + text), TablerIcon.create(icon));
  }
}