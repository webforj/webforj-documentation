package com.webforj.samples.verify;

import com.webforj.App;
import com.webforj.annotation.AppDarkTheme;
import com.webforj.annotation.AppLightTheme;
import com.webforj.annotation.AppTheme;
import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.security.annotation.AnonymousAccess;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VerifyBlogJavaAdminDashboardSnippets extends Composite<Div> {

  public VerifyBlogJavaAdminDashboardSnippets() {
    // Nested classes below are compiled as the snippets.
  }

  @Route
  public static class MainLayout extends Composite<AppLayout> {
    private final AppLayout self = getBoundComponent();

    public MainLayout() {
      Toolbar header = new Toolbar();
      header.addToStart(new AppDrawerToggle());
      header.addToTitle(new H1("Admin"));
      self.addToHeader(header).setHeaderOffscreen(false);

      AppNav nav = new AppNav();
      nav.addItem(
          new AppNavItem("Overview", OverviewView.class)
              .setPrefixComponent(TablerIcon.create("layout-dashboard")));
      nav.addItem(
          new AppNavItem("Orders", OrdersView.class)
              .setPrefixComponent(TablerIcon.create("shopping-cart")));
      nav.addItem(
          new AppNavItem("Users", UsersView.class).setPrefixComponent(TablerIcon.create("users")));

      self.addToDrawer(nav);
      self.setDrawerOpened(true);
    }
  }

  public record Order(String id, String customer, double total) {
    public String getId() {
      return id;
    }

    public String getCustomer() {
      return customer;
    }

    public double getTotal() {
      return total;
    }
  }

  @Route(outlet = MainLayout.class)
  @FrameTitle("Overview")
  @PermitAll
  public static class OverviewView extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();

    public OverviewView() {
      FlexLayout kpis =
          new FlexLayout(
              kpi("Revenue", "$48,210"), kpi("Orders", "1,284"), kpi("Active users", "312"));
      kpis.setSpacing("1em");

      GoogleChart chart = new GoogleChart(GoogleChart.Type.LINE);
      List<Object> data = new ArrayList<>();
      data.add(Arrays.asList("Month", "Revenue"));
      data.add(Arrays.asList("May", 31000));
      data.add(Arrays.asList("Jun", 36500));
      data.add(Arrays.asList("Jul", 48210));
      chart.setData(data);
      chart.setOptions(Map.of("title", "Revenue by month", "legend", "none"));

      Table<Order> table = new Table<>();
      table.addColumn("Order", Order::getId);
      table.addColumn("Customer", Order::getCustomer);
      table.addColumn("Total", Order::getTotal);
      table.setItems(
          List.of(new Order("A-1001", "Northwind", 420.0), new Order("A-1002", "Contoso", 1180.5)));

      self.setDirection(FlexDirection.COLUMN);
      self.setSpacing("1.5em");
      self.add(kpis, chart, table);
    }

    private Card kpi(String label, String value) {
      Card card = new Card(new H1(value));
      card.addToTitle(new H3(label));
      return card;
    }
  }

  @Route(value = "orders", outlet = MainLayout.class)
  @PermitAll
  public static class OrdersView extends Composite<Div> {}

  @Route(value = "users", outlet = MainLayout.class)
  @RolesAllowed("ADMIN")
  public static class UsersView extends Composite<Div> {}

  @Route("login")
  @AnonymousAccess
  public static class LoginView extends Composite<Div> {}

  @AppTheme("system")
  @AppLightTheme("light")
  @AppDarkTheme("dark")
  public static class Application extends App {}

  @Route(value = "toggle", outlet = MainLayout.class)
  public static class ThemeToggleView extends Composite<Div> {
    public ThemeToggleView() {
      IconButton toggle = new IconButton(TablerIcon.create("moon"));
      toggle.onClick(e -> App.setTheme("dark".equals(App.getTheme()) ? "light" : "dark"));
      getBoundComponent().add(toggle);
    }
  }
}
