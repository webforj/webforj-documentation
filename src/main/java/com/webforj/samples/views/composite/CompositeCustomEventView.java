package com.webforj.samples.views.composite;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.toast.Toast;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Route
@FrameTitle("Custom Events Demo")
@StyleSheet("ws://composite/composite.css")
public class CompositeCustomEventView extends Composite<Div> {

  H1 title = new H1("Product Order Form");
  OrderForm orderForm = new OrderForm();

  public CompositeCustomEventView() {
    getBoundComponent().addClassName("frame");

    orderForm.onOrderSubmitted((product, quantity) -> {
      Toast.show("Order placed successfully! " + quantity + "x " + product);
    });

    orderForm.onOrderFailed(errorMessage -> {
      Toast.show("Order failed: " + errorMessage);
    });

    getBoundComponent().add(title, orderForm);
  }
  
  public static class OrderForm extends Composite<FlexLayout> {
    
    private TextField productField;
    private ChoiceBox quantityChoice;
    private Button orderButton;
    
    private BiConsumer<String, Integer> orderSubmittedCallback;
    private Consumer<String> orderFailedCallback;

    public OrderForm() {
      initializeComponents();
      setupLayout();
      setupEventHandlers();
    }

    private void initializeComponents() {
      productField = new TextField()
          .setLabel("Product Name")
          .setPlaceholder("Enter product name")
          .setValue("Gaming Laptop");

      quantityChoice = new ChoiceBox()
          .setLabel("Quantity");
      
      for (int i = 1; i <= 10; i++) {
        quantityChoice.add(i, String.valueOf(i));
      }
      quantityChoice.selectIndex(1); 

      orderButton = new Button("Place Order")
          .setTheme(ButtonTheme.PRIMARY);
    }

    private void setupLayout() {
      getBoundComponent()
          .setDirection(FlexDirection.COLUMN)
          .setSpacing("var(--dwc-space-l)")
          .add(productField, quantityChoice, orderButton);
    }

    private void setupEventHandlers() {
      orderButton.onClick(e -> processOrder());
    }

    private void processOrder() {
      String product = productField.getValue().trim();
      
      int selectedIndex = quantityChoice.getSelectedIndex();
      if (selectedIndex < 0) {
        fireOrderFailed("Please select a quantity");
        return;
      }
      
      int quantity = selectedIndex + 1; 

      if (product.isEmpty()) {
        fireOrderFailed("Product name is required");
        return;
      }

      orderButton.setText("Processing...").setEnabled(false);
      
      fireOrderSubmitted(product, quantity);

      orderButton.setText("Place Order").setEnabled(true);
    }

    public OrderForm onOrderSubmitted(BiConsumer<String, Integer> callback) {
      this.orderSubmittedCallback = callback;
      return this;
    }

    public OrderForm onOrderFailed(Consumer<String> callback) {
      this.orderFailedCallback = callback;
      return this;
    }
    
    private void fireOrderSubmitted(String productName, int quantity) {
      if (orderSubmittedCallback != null) {
        orderSubmittedCallback.accept(productName, quantity);
      }
    }

    private void fireOrderFailed(String errorMessage) {
      if (orderFailedCallback != null) {
        orderFailedCallback.accept(errorMessage);
      }
    }
  }
}