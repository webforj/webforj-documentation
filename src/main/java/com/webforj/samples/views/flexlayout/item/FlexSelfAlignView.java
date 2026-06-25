package com.webforj.samples.views.flexlayout.item;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Item Self Align")
public class FlexSelfAlignView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  private final FlexLayout boxLayout;
  private final Button alignButton;

  public FlexSelfAlignView() {
    FlexLayout mainLayout = createMainLayout();
    this.boxLayout = createBoxLayout();
    this.alignButton = createButtons();
    ChoiceBox alignment = createAlignmentChoiceBox();

    boxLayout.setItemAlignment(FlexAlignment.START, alignButton);
    self.add(mainLayout);
    mainLayout.add(alignment, boxLayout);
  }

  private FlexLayout createMainLayout() {
    return FlexLayout.create().horizontal().build();
  }

  private FlexLayout createBoxLayout() {
    return FlexLayout.create().horizontal().wrap().build().addClassName("button__container");
  }

  private Button createButtons() {
    Button lastButton = null;
    for (int i = 1; i <= 5; i++) {
      Button newButton = new Button("Button " + i, ButtonTheme.PRIMARY);
      boxLayout.add(newButton);
      boxLayout.setItemOrder(i, newButton);
      lastButton = newButton;
    }
    lastButton.setTheme(ButtonTheme.DANGER).setText("Align Me!");
    return lastButton;
  }

  private ChoiceBox createAlignmentChoiceBox() {
    ChoiceBox alignment =
        new ChoiceBox().addClassName("flex__options").setLabel("Self Alignment Options");

    alignment.onSelect(
        e -> {
          FlexAlignment flexAlignment = FlexAlignment.fromValue(e.getSelectedItem().getText());
          boxLayout.setItemAlignment(flexAlignment, alignButton);
        });

    for (FlexAlignment align : FlexAlignment.values()) {
      String label = align.getValue();
      String key = align.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      alignment.add("." + key + "()", text);
    }
    alignment.selectIndex(0);

    return alignment;
  }
}
