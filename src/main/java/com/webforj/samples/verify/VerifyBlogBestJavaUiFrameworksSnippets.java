package com.webforj.samples.verify;

import com.webforj.annotation.JavaScript;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.component.toast.Toast;
import com.webforj.data.binding.BindingContext;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** Verification of the snippets in the best-java-ui-frameworks post. */
public class VerifyBlogBestJavaUiFrameworksSnippets extends Composite<Div> {

  public VerifyBlogBestJavaUiFrameworksSnippets() {
    // Snippets are compiled as nested classes.
  }

  public record Order(String number, String customer, BigDecimal total) {}

  // In the post this class is annotated with @Service (Spring is not a dependency here).
  public static class OrderService {
    public List<Order> findOpen() {
      return List.of();
    }
  }

  // In the post this class is annotated with @Service (Spring is not a dependency here).
  public static class CustomerService {
    public void save(Customer customer) {}
  }

  // Example 1
  @Route("/orders")
  @FrameTitle("Orders")
  public static class OrdersView extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();
    private final Table<Order> table = new Table<>();

    public OrdersView(OrderService orders) {
      self.setDirection(FlexDirection.COLUMN).setSpacing("1em");

      table.addColumn("Order", Order::number);
      table.addColumn("Customer", Order::customer);
      table.addColumn("Total", Order::total);
      table.setItems(orders.findOpen());

      Button refresh =
          new Button(
              "Refresh",
              ButtonTheme.PRIMARY,
              e -> {
                table.setItems(orders.findOpen());
                Toast.show("Orders reloaded", Theme.SUCCESS);
              });

      self.add(refresh, table);
    }
  }

  // Example 2
  public static class Customer {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter a valid email")
    private String email;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }

  @Route("/customers/new")
  public static class CustomerForm extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();
    private final TextField name = new TextField("Name");
    private final TextField email = new TextField("Email");
    private final Button save = new Button("Save", ButtonTheme.PRIMARY);
    private final BindingContext<Customer> context;

    public CustomerForm(CustomerService customers) {
      self.setDirection(FlexDirection.COLUMN).setSpacing("1em").add(name, email, save);

      context = BindingContext.of(this, Customer.class, true);
      context.onValidate(e -> save.setEnabled(e.isValid()));

      save.onClick(
          e -> {
            Customer customer = new Customer();
            if (context.write(customer).isValid()) {
              customers.save(customer);
            }
          });
    }
  }

  // Example 3
  @NodeName("wa-relative-time")
  @JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
  public static class RelativeTime extends ElementComposite {
    private final PropertyDescriptor<String> date = PropertyDescriptor.property("date", "");

    public RelativeTime setDate(Instant instant) {
      set(date, instant.toString());
      return this;
    }
  }
}
