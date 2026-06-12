---
title: "Disable a Button while a save operation runs"
description: "Prevent double-submit by disabling the button and updating its label for the duration of the operation, then restoring it when done."
tags: [forms, components]
components: [Button]
difficulty: beginner
---

Disable the button at the start of the click handler and re-enable it in a `finally` block so it's always restored, even if the operation throws. In this example, `OrderService.persist` is the app-specific save method; replace its body with your repository, REST client, or database call.

```java
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.Route;

@Route("order-save")
public class OrderSaveView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);
  private final OrderService orderService = new OrderService();
  private final Order currentOrder = new Order("A-1001");

  public OrderSaveView() {
    save.onClick(e -> saveOrder());
    self.add(save);
  }

  private void saveOrder() {
    save.setEnabled(false);
    save.setText("Saving...");
    try {
      orderService.persist(currentOrder);
    } finally {
      save.setEnabled(true);
      save.setText("Save");
    }
  }

  public record Order(String orderNumber) {}

  public static class OrderService {
    public void persist(Order order) {
      // Replace this with your repository, REST client, or database call.
    }
  }
}
```
