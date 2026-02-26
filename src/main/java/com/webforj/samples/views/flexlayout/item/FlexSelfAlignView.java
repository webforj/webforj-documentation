package com.webforj.samples.views.flexlayout.item;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Item Self Align")
public class FlexSelfAlignView extends Composite<Div> {
  private Div self = getBoundComponent();
  private FlexLayout boxLayout;
  private Button alignButton;

  public FlexSelfAlignView() {
    FlexLayout mainLayout = FlexLayout.create()
            .horizontal()
            .build();

    this.boxLayout = FlexLayout.create()
            .horizontal()
            .wrap().wrap()
            .build()
            .addClassName("button__container");

    for (int i = 1; i <= 5; i++) {
      Button newButton = new Button("Button " + i, ButtonTheme.PRIMARY);
      boxLayout.add(newButton);
      boxLayout.setItemOrder(i, newButton);
      alignButton = newButton;
    }
    alignButton.setTheme(ButtonTheme.DANGER).setText("Align Me!");

    ChoiceBox alignment = new ChoiceBox()
            .addClassName("flex__options")
            .setLabel("Self Alignment Options");
    alignment.onSelect(e -> {
      FlexAlignment flexAlignment = FlexAlignment.fromValue(e.getSelectedItem().getText());
      boxLayout.setItemAlignment(flexAlignment, alignButton);
    });

    for (FlexAlignment align : FlexAlignment.values()) {
      String label = align.getValue();
      String key = align.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase()
              + label.substring(1);
      alignment.add(
              "." + key + "()", text);
    }
    alignment.selectIndex(0);

    boxLayout.setItemAlignment(FlexAlignment.START, alignButton);
    self.add(mainLayout);
    mainLayout.add(alignment, boxLayout);
  }
}
