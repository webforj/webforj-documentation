package com.webforj.samples.views.flexlayout;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.button.event.ButtonClickEvent;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexLayout;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

@Route
@FrameTitle("Flex Basis")
public class FlexBasisView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();

  private final FlexLayout mainLayout;
  private final FlexLayout boxLayout;
  private final FlexLayout optionLayout;
  private final List<Button> buttons;
  private final NumberField numberField;
  private final Button basisButton;
  private final Button reset;

  private int selected = 0;

  public FlexBasisView() {
    numberField = createNumberField();
    basisButton = createBasisButton();
    reset = createResetButton();
    buttons = createButtons();
    boxLayout = createBoxLayout();
    optionLayout = createOptionLayout();
    mainLayout = createMainLayout();

    setupMainLayout();
  }

  private List<Button> createButtons() {
    List<Button> buttonList = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      Button button = new Button("Box " + i, ButtonTheme.OUTLINED_PRIMARY, this::onButtonSelect);
      button.setStyle("transition", "flex-basis var(--dwc-transition-medium) var(--dwc-ease-inOutExpo)");
      buttonList.add(button);
    }
    return buttonList;
  }

  private FlexLayout createMainLayout() {
    return FlexLayout.create(optionLayout, boxLayout)
        .horizontal()
        .build()
        .setPadding("20px")
        .setItemBasis("100%", boxLayout);
  }

  private FlexLayout createBoxLayout() {
    FlexLayout layout = FlexLayout.create()
        .horizontal()
        .wrap()
        .build()
        .setPadding("20px")
        .setStyle("border", "1px solid var(--dwc-color-default)");

    for (Button button : buttons) {
      layout.add(button);
      layout.setItemBasis("75px", button);
    }

    return layout;
  }

  private NumberField createNumberField() {
    return new NumberField("Basis")
        .setMin(75.0)
        .setTooltipText("Set the flex basis width (in pixels)")
        .setRequired(true);
  }

  private Button createBasisButton() {
    return new Button("Set basis", this::onSetBasis)
        .setTooltipText("Select a box item first");
  }

  private Button createResetButton() {
    return new Button("Reset", ButtonTheme.OUTLINED_GRAY, this::onReset);
  }

  private FlexLayout createOptionLayout() {
    return FlexLayout.create(numberField, basisButton, reset)
        .vertical()
        .build();
  }

  private void setupMainLayout() {
    self.add(mainLayout);
  }

  private void onButtonSelect(ButtonClickEvent e) {
    Button eventButton = (Button) e.getComponent();
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
