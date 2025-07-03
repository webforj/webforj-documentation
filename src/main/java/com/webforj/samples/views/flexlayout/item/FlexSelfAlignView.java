package com.webforj.samples.views.flexlayout.item;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/flexlayout/container/flexContainerBuilder.css")
@Route(RouteConfig.FLEX_SELF_ALIGN)
@FrameTitle("Flex Item Self Align")
public class FlexSelfAlignView extends Composite<Div> {

  FlexLayout boxLayout;
  Button alignButton;

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

    ChoiceBox alignment = new ChoiceBox();
    alignment
        .onSelect(e -> boxLayout.setItemAlignment(FlexAlignment.fromValue(e.getSelectedItem().getText()), alignButton));
    alignment.addClassName("flex__options");

    alignment.setLabel("Self Alignment Options");
    for (FlexAlignment align : FlexAlignment.values()) {
      String label = align.getValue();
      alignment.add(
          "." + align.toString()
              .toLowerCase() + "()",
          label.substring(0, 1)
              .toUpperCase()
              + label
                  .substring(1));
    }
    alignment.selectIndex(0);

    boxLayout.setItemAlignment(FlexAlignment.START, alignButton);
    getBoundComponent().add(mainLayout);
    mainLayout.add(alignment, boxLayout);
  }
}
