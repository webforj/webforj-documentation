---
title: "Disable a Button while a save operation runs"
description: "Prevent double-submit by disabling the button and updating its label for the duration of the operation, then restoring it when done."
tags: [forms, components]
components: [Button]
difficulty: beginner
---

Disable the button before submitting the save to a worker thread. Use `Environment.runLater()` for the final UI update because webforJ components can only be changed from the environment thread.

```java
import com.webforj.Environment;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.Route;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Route("order-save")
public class OrderSaveView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);
  private final OrderService orderService = new OrderService();
  private final Order currentOrder = new Order("A-1001");
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread worker = new Thread(r, "order-save-worker");
    worker.setDaemon(true);
    return worker;
  });
  private CompletableFuture<Void> currentSave;

  public OrderSaveView() {
    save.onClick(e -> saveOrder());
    self.add(save);
  }

  private void saveOrder() {
    if (currentSave != null && !currentSave.isDone()) {
      return;
    }

    save.setEnabled(false);
    save.setText("Saving...");

    currentSave = CompletableFuture.runAsync(
        () -> orderService.persist(currentOrder),
        executor);

    currentSave.whenComplete((result, error) -> Environment.runLater(() -> {
      if (!save.isDestroyed()) {
        save.setEnabled(true);
        save.setText(error == null ? "Saved" : "Try again");
      }
    }));
  }

  @Override
  protected void onDidDestroy() {
    if (currentSave != null) {
      currentSave.cancel(true);
    }
    executor.shutdownNow();
  }

  public record Order(String orderNumber) {}

  public static class OrderService {
    public void persist(Order order) {
      // Replace this with your repository, REST client, or database call.
    }
  }
}
```
