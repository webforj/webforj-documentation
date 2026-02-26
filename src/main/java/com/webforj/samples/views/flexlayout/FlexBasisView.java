package com.webforj.samples.views.flexlayout;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.button.DwcButton;
import com.webforj.component.button.event.ButtonClickEvent;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

@Route
@FrameTitle("Flex Basis")
public class FlexBasisView extends Composite<Div> {
  private Div self = getBoundComponent();
  private FlexLayout mainLayout;
  private FlexLayout boxLayout;
  private FlexLayout optionLayout;
  private List<Button> buttons;
  private NumberField numberField;
  private Button basisButton;
  private Button reset;

  private int selected = 0;

  public FlexBasisView() {
    mainLayout = FlexLayout.create()
            .horizontal()
            .build()
            .setPadding("20px")
            .setItemBasis("100%", boxLayout);

    boxLayout = FlexLayout.create()
            .horizontal()
            .wrap().wrap()
            .build()
            .setPadding("20px")
            .setStyle("border", "1px solid var(--dwc-color-default)");

    buttons = new ArrayList<>();

    for (int i = 1; i <= 5; i++) {
      Button newButton = new Button("Box " + i, ButtonTheme.OUTLINED_PRIMARY, this::onButtonSelect);
      newButton.setStyle("transition", "flex-basis var(--dwc-transition-medium) var(--dwc-ease-inOutExpo)");
      buttons.add(newButton);
      boxLayout.add(buttons.get(i - 1));
      boxLayout.setItemBasis("75px", buttons.get(i - 1));
    }

    numberField = new NumberField("Basis")
            .setMin(75.0)
            .setTooltipText("Set the flex basis width (in pixels)")
            .setRequired(true);

    basisButton = new Button("Set basis", this::onSetBasis)
            .setTooltipText("Select a box item first");

    reset = new Button("Reset", ButtonTheme.OUTLINED_GRAY, this::onReset);

    optionLayout = FlexLayout.create(numberField, basisButton, reset)
            .vertical()
            .build();

    self.add(mainLayout);
    mainLayout.add(optionLayout, boxLayout);
  }

  private void onButtonSelect(ButtonClickEvent e) {
    DwcButton<?> eventButton = e.getComponent();
    if (eventButton.getTheme() == ButtonTheme.OUTLINED_PRIMARY) {
      eventButton.setTheme(ButtonTheme.PRIMARY);
      basisButton.setTooltipText("Set the basis for " + ++selected + " box item(s)");
    } else {
      eventButton.setTheme(ButtonTheme.OUTLINED_PRIMARY);
      basisButton.setTooltipText("Set the basis for " + --selected + " box item(s)");
    }
  }

  private void onSetBasis(ButtonClickEvent e) {
    if (numberField.getValue() != null) {
      for (Button button : buttons) {
        if (button.getTheme() == ButtonTheme.PRIMARY) {
          boxLayout.setItemBasis(numberField.getValue() + "px", button);
        } else {
          boxLayout.setItemBasis("75px", button);
        }
      }
    }
  }

  private void onReset(ButtonClickEvent e) {
    basisButton.setTooltipText("Select a box item first");
    selected = 0;
    for (Button button : buttons) {
      button.setTheme(ButtonTheme.OUTLINED_PRIMARY);
      boxLayout.setItemBasis("75px", button);
    }
  }
}
