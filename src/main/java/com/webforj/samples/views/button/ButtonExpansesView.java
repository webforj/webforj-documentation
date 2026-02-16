package com.webforj.samples.views.button;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Simple program to demonstrate the various Button expanse values.
 */
@Route
@FrameTitle("Button Expanses")
public class ButtonExpansesView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private ChoiceBox expanses = new ChoiceBox();
  private Button demoButton = new Button("None");

  public ButtonExpansesView() {
    self.setSpacing("var(--dwc-space-l)")
            .setMargin("var(--dwc-space-l)")
            .add(expanses, demoButton);

    List<ListItem> categories = Arrays.asList(Expanse.values())
            .reversed()
            .stream()
            .filter(Predicate.not(Predicate.isEqual(Expanse.NONE)))
            .map(expanse -> new ListItem(expanse, expanse.name()))
            .toList();

    expanses.insert(categories)
            .selectIndex(0)
            .setWidth("100px")
            .addSelectListener(event -> {
              Expanse selectedValue = (Expanse) expanses.getSelectedKey();
              if (selectedValue != null) {
                demoButton.setExpanse(selectedValue);
                demoButton.setText(selectedValue.name());
              }
            });
  }
}