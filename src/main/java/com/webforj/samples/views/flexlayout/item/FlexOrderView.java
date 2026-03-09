package com.webforj.samples.views.flexlayout.item;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@Route
@FrameTitle("Flex Order")
public class FlexOrderView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();

  private final FlexLayout boxLayout;
  private final Button orderButton;

  public FlexOrderView() {
    FlexLayout mainLayout = createMainLayout();
    this.boxLayout = createBoxLayout();
    this.orderButton = createButtons();
    MaskedNumberField order = createOrderField();
    Button submit = createSubmitButton(order);

    FlexLayout optionLayout = FlexLayout.create(order, submit)
        .vertical()
        .align().stretch()
        .build()
        .addClassName("flex__options");

    self.add(mainLayout);
    mainLayout.add(optionLayout, boxLayout);
  }

  private FlexLayout createMainLayout() {
    return FlexLayout.create()
        .horizontal()
        .align().start()
        .build();
  }

  private FlexLayout createBoxLayout() {
    return FlexLayout.create()
        .horizontal()
        .wrap()
        .build()
        .addClassName("button__container--single-row");
  }

  private Button createButtons() {
    Button lastButton = null;
    for (int i = 1; i <= 5; i++) {
      Button newButton = new Button("Order: " + i, ButtonTheme.PRIMARY);
      boxLayout.add(newButton);
      boxLayout.setItemOrder(i, newButton);
      lastButton = newButton;
    }
    lastButton.setTheme(ButtonTheme.DANGER);
    return lastButton;
  }

  private MaskedNumberField createOrderField() {
    return new MaskedNumberField("5")
        .setLabel("Order:")
        .setWidth("100%")
        .setInvalidMessage("Order can not be empty.")
        .setNegateable(false);
  }

  private Button createSubmitButton(MaskedNumberField order) {
    Button button = new Button("Set Order")
        .setSize("100%", "34px");
    button.onClick(e -> {
          if (order.getText().isEmpty()) {
            order.setInvalid(true);
          } else {
            int itemOrder = Integer.parseInt(order.getText());
            boxLayout.setItemOrder(itemOrder, orderButton);
            orderButton.setText("Order: " + order.getText());
          }
        });
    return button;
  }
}
