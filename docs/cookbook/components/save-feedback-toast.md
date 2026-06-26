---
title: "Show a success or error Toast after a save action"
description: "Use Toast.show with a success or danger theme to give the user immediate, non-blocking feedback after a server-side operation."
tags: [notification, components]
components: [Toast]
difficulty: beginner
---

Wrap the operation in a try/catch and call `Toast.show` with the appropriate `Theme` in each branch. In this example, `CustomerService.persist` represents the part of your app that writes to a repository, REST endpoint, or database.

```java
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.Route;

@Route("customer-save")
public class CustomerSaveView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final CustomerService customerService = new CustomerService();
  private final Customer currentCustomer = new Customer("Ada Lovelace");

  public CustomerSaveView() {
    Button save = new Button("Save", ButtonTheme.PRIMARY);

    save.onClick(e -> {
      try {
        customerService.persist(currentCustomer);
        Toast.show("Changes saved.", Theme.SUCCESS);
      } catch (RuntimeException ex) {
        Toast.show("Save failed: " + ex.getMessage(), Theme.DANGER);
      }
    });

    self.add(save);
  }

  public record Customer(String name) {}

  public static class CustomerService {
    public void persist(Customer customer) {
      // Replace this with your repository, REST client, or database call.
    }
  }
}
```
