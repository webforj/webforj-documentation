package com.webforj.samples.views.flexlayout.item;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@Route
@FrameTitle("Flex Order")
public class FlexOrderView extends Composite<Div> {
  private Div self = getBoundComponent();
  private FlexLayout boxLayout;
  private Button orderButton;

  public FlexOrderView() {
    FlexLayout mainLayout = FlexLayout.create()
            .horizontal()
            .align().start()
            .build();

    this.boxLayout = FlexLayout.create()
            .horizontal()
            .wrap().wrap()
            .build()
            .addClassName("button__container--single-row");

    for (int i = 1; i <= 5; i++) {
      Button newButton = new Button("Order: " + i, ButtonTheme.PRIMARY);
      boxLayout.add(newButton);
      boxLayout.setItemOrder(i, newButton);
      orderButton = newButton;
    }
    orderButton.setTheme(ButtonTheme.DANGER);

    MaskedNumberField order = new MaskedNumberField("5")
            .setLabel("Order:")
            .setWidth("100%")
            .setInvalidMessage("Order can not be empty.")
            .setNegateable(false);

    Button submit = new Button("Set Order");
    submit.setSize("100%", "34px")
            .onClick(e -> {
              if (order.getText().isEmpty()) {
                order.setInvalid(true);
              } else {
                int itemOrder = Integer.parseInt(order.getText());
                boxLayout.setItemOrder(itemOrder, orderButton);
                orderButton.setText("Order: " + order.getText());
              }
            });

    FlexLayout optionLayout = FlexLayout.create(order, submit)
            .vertical()
            .align().stretch()
            .build()
            .addClassName("flex__options");

    self.add(mainLayout);
    mainLayout.add(optionLayout, boxLayout);
  }
}
