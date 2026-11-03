package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.List;

/** Verification of the snippets in the Spring Boot frontend post. */
public class VerifyBlogSpringBootFrontendSnippets extends Composite<Div> {

  public VerifyBlogSpringBootFrontendSnippets() {
    // The snippets below are compiled as nested classes.
  }

  public static class Order {
    public String getNumber() {
      return "";
    }

    public String getCustomer() {
      return "";
    }

    public String getStatus() {
      return "";
    }
  }

  // In the post this class is annotated with @Service (Spring is not a dependency here).
  public static class OrderService {
    public List<Order> findOpenOrders() {
      return new ArrayList<>();
    }

    public void ship(Order order) {}
  }

  // Example 1: a view with a constructor-injected service
  @Route("/orders")
  public static class OrdersView extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();
    private final Table<Order> table = new Table<>();
    private final Button ship = new Button("Ship selected");

    public OrdersView(OrderService orders) {
      self.setDirection(FlexDirection.COLUMN).setSpacing("1em");

      table.addColumn("Order", Order::getNumber);
      table.addColumn("Customer", Order::getCustomer);
      table.addColumn("Status", Order::getStatus);
      table.setRepository(new CollectionRepository<>(orders.findOpenOrders()));

      ship.onClick(
          e -> {
            table.getSelectedItems().forEach(orders::ship);
            table.setRepository(new CollectionRepository<>(orders.findOpenOrders()));
          });

      self.add(table, ship);
    }
  }

  // Not compiled here (Spring and Spring Security are not dependencies of this project):
  //
  // <dependency>
  //   <groupId>com.webforj</groupId>
  //   <artifactId>webforj-spring-boot-starter</artifactId>
  // </dependency>
  //
  // @Route(value = "/teams", outlet = MainLayout.class)
  // @RolesAllowed("ADMIN")
  // public class TeamsView extends Composite<FlexLayout> { ... }
  //
  // @Bean
  // SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //   return http
  //       .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
  //           .loginPage(LoginView.class)
  //           .accessDeniedPage(AccessDenyView.class)
  //           .logout())
  //       .build();
  // }
}
